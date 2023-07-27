package com.intershipProject.QuizApp.Repository;

import com.intershipProject.QuizApp.Model.Course;
import com.intershipProject.QuizApp.Model.User;
import com.intershipProject.QuizApp.Model.UserQuiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserQuizRepo extends JpaRepository<UserQuiz, Long> {

    List<UserQuiz> findByUserIdAndCourseId(Long userId, Long courseId);

    UserQuiz findTopByUserIdAndCourseIdOrderByScoreDesc(Long userId, Long courseId);

    List<UserQuiz> findByUserId(Long id);


    List<UserQuiz> findTop3ByOrderByIdDesc();

    List<UserQuiz> findTop3ByUserIdOrderByScoreDesc(Long id);
}
