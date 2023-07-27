package com.intershipProject.QuizApp.Repository;

import com.intershipProject.QuizApp.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User , Long> {
    User findBySessionId(String sessionId);
    User findByUsername(String username);
}
