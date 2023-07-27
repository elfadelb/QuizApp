package com.intershipProject.QuizApp.Model;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @OneToMany(mappedBy = "course" , cascade = CascadeType.ALL)
    private List<Question> questions;
    @OneToMany(mappedBy = "course" , cascade = CascadeType.ALL)
    private List<UserQuiz> userQuizzes;

    public List<UserQuiz> getUserQuizzes() { return userQuizzes; }
    public void setUserQuizzes(List<UserQuiz> userQuizzes) { this.userQuizzes = userQuizzes; }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Question> getQuestions() {
        return questions;
    }
    public void setQuestions(List<Question> questions) { this.questions = questions; }
    public int getTotalQuestionCount() {
        if (questions == null) { return 0; }
        return questions.size();
    }
}
