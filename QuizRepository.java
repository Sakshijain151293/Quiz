package com.example.demo.Respository;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Quiz;

// QuizRepository.java
@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    @Query("SELECT q FROM Quiz q WHERE q.endDate > :today AND q.isActive = true")
    List<Quiz> findActiveQuizzes(@Param("today") Date today);

	Quiz findFirstByStartDateBeforeAndEndDateAfter(LocalDateTime now, LocalDateTime now2);

}


