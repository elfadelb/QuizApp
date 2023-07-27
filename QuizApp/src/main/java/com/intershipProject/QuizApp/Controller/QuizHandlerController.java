package com.intershipProject.QuizApp.Controller;

import com.intershipProject.QuizApp.Model.*;
import com.intershipProject.QuizApp.Repository.QuestionRepo;
import com.intershipProject.QuizApp.Repository.UserQuizRepo;
import com.intershipProject.QuizApp.Repository.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@RestController
public class QuizHandlerController {
    @Autowired
    private QuestionRepo questionRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserQuizRepo userQuizRepo;


    private static final Logger logger = LogManager.getLogger(QuizHandlerController.class);


    @GetMapping("/quiz/{id}")
    public ModelAndView getQuiz(@PathVariable Long id , HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view/html/user/quiz");
        List<Question> easyQuestion = questionRepo.findAllByCourseIdAndDifficulty(id, Difficulty.easy);
        List<Question> mediumQuestion = questionRepo.findAllByCourseIdAndDifficulty(id, Difficulty.normal);
        List<Question> hardQuestion = questionRepo.findAllByCourseIdAndDifficulty(id, Difficulty.difficult);


        Collections.shuffle(easyQuestion);
        Collections.shuffle(mediumQuestion);
        Collections.shuffle(hardQuestion);

        List<Question> questions = new ArrayList<>();
        questions.addAll(easyQuestion.subList(0, 5));
        questions.addAll(mediumQuestion.subList(0, 5));
        questions.addAll(hardQuestion.subList(0, 5));

        Collections.shuffle(questions);

        HttpSession session = request.getSession();
        String sessionId = (String) session.getAttribute("sessionId");
        User loggedInUser = userRepo.findBySessionId(sessionId);

        modelAndView.addObject("user", loggedInUser);
        modelAndView.addObject("questions",questions);

        return modelAndView;
    }
    @PostMapping("/check-quiz")
    public ModelAndView checkQuiz(@RequestParam Map<String, String> params, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String sessionId = (String) session.getAttribute("sessionId");
        User loggedInUser = userRepo.findBySessionId(sessionId);

        // Retrieve the questions from the form submission
        List<Question> questions = new ArrayList<>();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            String paramName = entry.getKey();
            if (paramName.startsWith("choice")) {
                Long questionId = Long.parseLong(paramName.substring("choice".length()));
                Question question = questionRepo.findById(questionId).orElse(null);
                if (question != null) {
                    questions.add(question);
                }
            }
        }


        float score = 0;
        // Calculate the score
        boolean timerExpired = "true".equals(params.get("timerExpired"));
        if(!timerExpired){

            for (Question question : questions) {
                String questionIndex = Long.toString(question.getId());

                // Retrieve the selected choice for the question
                String selectedChoiceValue = params.get("choice" + questionIndex);

                // Compare the selected choice with the correct answer from the database
                if (selectedChoiceValue != null && !selectedChoiceValue.isEmpty()) {
                    if (selectedChoiceValue.equals(question.getCorrectAnswer())) {
                        score += 1; // Increase the score by 1 for each correct answer
                    }
                } else {
                    // If the question is not answered, set the score for that question to 0
                    score += 0;
                }
            }
        }


        // Calculate the percentage score and format it to two decimal places
        float percentageScore = (float) ((score) * 100.0 / 15.0);
        String formattedScore = String.format("%.2f", percentageScore); // Format with two decimal places using a dot (.)



        // Create or update the UserQuiz entity
        Course course = null;
        if (questions.size() > 0) {
            course = questions.get(0).getCourse(); // Assuming all questions belong to the same course
        }



        UserQuiz userQuiz = new UserQuiz();
        userQuiz.setUser(loggedInUser);
        userQuiz.setCourse(course);
        userQuiz.setScore(Float.parseFloat(formattedScore.replace(",", ".")));

        // Save the UserQuiz entity to the database
        userQuizRepo.save(userQuiz);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/user-dashboard");
        return modelAndView;// Redirect to a page displaying the quiz result
    }





}
