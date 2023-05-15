package com.example.demo.Service;

import com.example.demo.Respository.QuizRepository;
import com.example.demo.entities.Quiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    public Quiz createQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public Quiz getActiveQuiz() {
        LocalDateTime now = LocalDateTime.now();
        return quizRepository.findFirstByStartDateBeforeAndEndDateAfter(now, now);
    }

    public Quiz getQuizById(Long id) {
        return quizRepository.findById(id).orElse(null);
    }

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public String getQuizStatus(Quiz quiz) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(quiz.getStartDate())) {
            return "inactive";
        } else if (now.isBefore(quiz.getEndDate())) {
            return "active";
        } else {
            return "finished";
        }
    }
}
