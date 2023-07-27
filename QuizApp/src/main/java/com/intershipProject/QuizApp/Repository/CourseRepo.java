package com.intershipProject.QuizApp.Repository;

import com.intershipProject.QuizApp.Model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepo extends JpaRepository<Course, Long> {
    List<Course> findTop5ByOrderByIdDesc();

    List<Course> findTop3ByOrderByIdDesc();
}
