package com.intershipProject.QuizApp.Controller;

import com.intershipProject.QuizApp.Model.Question;
import com.intershipProject.QuizApp.Repository.QuestionRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import java.util.Optional;

@RestController
public class QuestionController {
    @Autowired
    private QuestionRepo questionRepo;
    @GetMapping("/get-question")
    public List<Question> getQuestion(){
        return questionRepo.findAll();
    }

    @PostMapping(value = "/create-question", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView saveQuestion(@ModelAttribute Question question, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin-create-question");

        // Perform manual validation checks
        if (question.getQuestionContent().isEmpty() || question.getChoice1().isEmpty()
                || question.getChoice2().isEmpty() || question.getChoice3().isEmpty()
                || question.getChoice4().isEmpty() || question.getCorrectAnswer().isEmpty()) {
            String errorMessage = "Invalid question data. Please check your input.";
            request.getSession().setAttribute("errorMessage", errorMessage);
            return modelAndView;
        }

        if (questionRepo.save(question) == null) {
            String errorMessage = "Failed to save the question. Please try again.";
            request.getSession().setAttribute("errorMessage", errorMessage);
        } else {
            String message = "Question saved successfully.";
            request.getSession().setAttribute("message", message);
        }

        return modelAndView;
    }

    @PostMapping("/update-question")
    public ModelAndView updateQuestion(@ModelAttribute Question question, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin-update-question/" + question.getId());

        Optional<Question> optionalQuestion = questionRepo.findById(question.getId());
        if (optionalQuestion.isEmpty()) {
            String errorMessage = "Invalid question data. Please check your input.";
            request.getSession().setAttribute("errorMessage", errorMessage);
            return modelAndView;
        }

        Question updatedQuestion = optionalQuestion.get();

        // Perform validation checks
        if (question.getQuestionContent().isEmpty() || question.getChoice1().isEmpty()
                || question.getChoice2().isEmpty() || question.getChoice3().isEmpty()
                || question.getChoice4().isEmpty() || question.getCorrectAnswer().isEmpty()) {
            String errorMessage = "Invalid question data. Please check your input.";
            request.getSession().setAttribute("errorMessage", errorMessage);
            return modelAndView;
        }

        // Update the question with the new values
        updatedQuestion.setQuestionContent(question.getQuestionContent());
        updatedQuestion.setChoice1(question.getChoice1());
        updatedQuestion.setChoice2(question.getChoice2());
        updatedQuestion.setChoice3(question.getChoice3());
        updatedQuestion.setChoice4(question.getChoice4());
        updatedQuestion.setCorrectAnswer(question.getCorrectAnswer());
        updatedQuestion.setDifficulty(question.getDifficulty());

        if (questionRepo.save(updatedQuestion) == null) {
            String errorMessage = "Failed to save the question. Please try again.";
            request.getSession().setAttribute("errorMessage", errorMessage);
        } else {
            String message = "Question Updated successfully.";
            request.getSession().setAttribute("message", message);
        }

        return modelAndView;
    }


    @GetMapping("/delete-question/{id}")
    public ModelAndView deleteQuestion(@PathVariable Long id, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin-show-question");

        Optional<Question> optionalQuestion = questionRepo.findById(id);
        if (optionalQuestion.isEmpty()) {
            String errorMessage = "Invalid question ID. Please check the ID.";
            request.getSession().setAttribute("errorMessage", errorMessage);
            return modelAndView;
        }

        Question question = optionalQuestion.get();
        questionRepo.delete(question);

        String message = "Question deleted successfully.";
        request.getSession().setAttribute("message", message);

        return modelAndView;
    }


}