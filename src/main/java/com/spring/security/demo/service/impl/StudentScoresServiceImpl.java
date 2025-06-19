package com.spring.security.demo.service.impl;

import com.spring.security.demo.service.StudentScoresService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StudentScoresServiceImpl implements StudentScoresService {

    private static final Logger LOG = LoggerFactory.getLogger(StudentScoresServiceImpl.class);

    public Map<String, Integer> getScores(String studentId) {
        LOG.debug("Fetching scores for student ID: {}", studentId);
        Map<String, Integer> scores = new HashMap<>();
        scores.put("Mathematics", 85);
        scores.put("English", 92);
        scores.put("History", 78);
        scores.put("Science", 88);
        scores.put("Art", 95);

        return scores;
    }

    public Integer getAverageScore(String studentId) {
        LOG.debug("Calculating average score for student ID: {} ", studentId);
        var scores = getScores(studentId);
        int total = scores.values().stream().mapToInt(Integer::intValue).sum();
        return total / scores.size();
    }

}
