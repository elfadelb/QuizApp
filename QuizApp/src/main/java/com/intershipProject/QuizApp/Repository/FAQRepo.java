package com.intershipProject.QuizApp.Repository;

import com.intershipProject.QuizApp.Model.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FAQRepo extends JpaRepository<FAQ, Long> {

}
