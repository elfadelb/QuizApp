package com.intershipProject.QuizApp.Repository;

import com.intershipProject.QuizApp.Model.Announcement;
import com.intershipProject.QuizApp.Model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepo extends JpaRepository<Announcement, Long> {
    List<Announcement> findTop5ByOrderByCreatedDateDesc();
}
