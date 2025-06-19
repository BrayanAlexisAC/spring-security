package com.spring.security.demo.controller;

import com.spring.security.demo.service.StudentScoresService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/student/scores")
public class StudentScoresController {

    private final StudentScoresService studentScoresService;

    @GetMapping("/{studentId}")
    public ResponseEntity<Map<String, Integer>> getScores(@PathVariable String studentId) {
        var scores = studentScoresService.getScores(studentId);
        return ResponseEntity.ok(scores);
    }

    @GetMapping("/average/{studentId}")
    public ResponseEntity<Integer> getAverageScore(@PathVariable String studentId) {
        var averageScore = studentScoresService.getAverageScore(studentId);
        return ResponseEntity.ok(averageScore);
    }

    public StudentScoresController (StudentScoresService studentScoresService) {
        this.studentScoresService = studentScoresService;
    }

}
