package com.intershipProject.QuizApp.Repository;

import com.intershipProject.QuizApp.Model.Difficulty;
import com.intershipProject.QuizApp.Model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface QuestionRepo extends JpaRepository<Question, Long> {
    List<Question> findAllByCourseId(Long courseId);
    List<Question> findAllByDifficulty(Difficulty difficulty);
    List<Question> findAllByCourseIdAndDifficulty(Long id ,Difficulty difficulty);
    @Query("SELECT q FROM Question q WHERE q.id = :questionId AND q.correctAnswer = :selectedChoice")
    Question findQuestionByIdAndSelectedChoice(Long questionId, String selectedChoice);

}
