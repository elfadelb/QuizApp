package com.intershipProject.QuizApp.Controller;

import com.intershipProject.QuizApp.Model.UserQuiz;
import com.intershipProject.QuizApp.Repository.UserQuizRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserQuizController {
    @Autowired
    private UserQuizRepo userQuizRepo;

    @PostMapping("/save-userQuiz")
    public String saveUserQuiz(@RequestBody UserQuiz userQuiz){
        userQuizRepo.save(userQuiz);
        return "User Quiz Created Successfully...";
    }
    @PutMapping("/update-userQuiz/{id}")
    public String updateUserQuiz(@RequestBody UserQuiz userQuiz , @PathVariable Long id){
        UserQuiz updatedUserQuiz = userQuizRepo.findById(id).get();
        updatedUserQuiz.setUser(userQuiz.getUser());
        updatedUserQuiz.setScore(userQuiz.getScore());
        userQuizRepo.save(updatedUserQuiz);
        return "User Quiz Updated Successfully...";
    }
    @DeleteMapping("/delete-userQuiz/{id}")
    public String deleteUserQuiz(@PathVariable Long id){
        UserQuiz deletedUserQuiz = userQuizRepo.findById(id).get();
        userQuizRepo.delete(deletedUserQuiz);
        return " User Quiz deleted Successfully....";
    }
}
