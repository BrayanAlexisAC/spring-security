package com.spring.security.demo.service;

import java.util.Map;

public interface StudentScoresService {

    /**
     * Retrieves the scores for a student.
     *
     * @return a map containing the subject names as keys and their corresponding scores as values.
     */
    Map<String, Integer> getScores(String studentId);

    /**
     * Retrieves the average score for a student.
     *
     * @param studentId the ID of the student
     * @return the average score as an Integer
     */
    Integer getAverageScore(String studentId);
}
