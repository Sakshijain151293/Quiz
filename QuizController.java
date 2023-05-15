package com.example.demo.Controller;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Respository.QuizRepository;
import com.example.demo.entities.Quiz;

@RestController
@RequestMapping("/quizzes")
public class QuizController {

    @Autowired
    private QuizRepository quizRepository;

    @PostMapping
    public ResponseEntity<?> createQuiz(@RequestBody Quiz quiz) {
        if (quiz.getStartDate().isAfter(quiz.getEndDate())) {
            return ResponseEntity.badRequest().body("End date must be after start date");
        }

        Quiz savedQuiz = quizRepository.save(quiz);

        return ResponseEntity.ok(savedQuiz);
    }

    @GetMapping("/active")
    public ResponseEntity<?> getActiveQuiz() {
        Date currentDate = new Date(0);

        List<Quiz> activeQuizzes = quizRepository.findActiveQuizzes(currentDate);

        if (activeQuizzes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(activeQuizzes.get(0));
    }

    @GetMapping("/{id}/result")
    public ResponseEntity<?> getQuizResult(@PathVariable("id") Long id) {
        Optional<Quiz> quiz = quizRepository.findById(id);

        if (!quiz.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        if (quiz.get().getEndDate().toLocalDate().atStartOfDay().isAfter(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Quiz is still active");
        }

        return ResponseEntity.ok(quiz.get().getRightAnswer());
    }


    @GetMapping("/all")
    public ResponseEntity<?> getAllQuizzes() {
        List<Quiz> quizzes = quizRepository.findAll();

        return ResponseEntity.ok(quizzes);
    }
}
