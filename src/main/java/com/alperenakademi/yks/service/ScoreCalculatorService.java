package com.alperenakademi.yks.service;

import com.alperenakademi.yks.model.LessonResult;
import com.alperenakademi.yks.model.MockExam;

import java.util.HashMap;
import java.util.Map;

public class ScoreCalculatorService {
    private final Map<String, Double> coefficients = new HashMap<>();

    public ScoreCalculatorService() {
        coefficients.put("Türkçe", 3.2);
        coefficients.put("Matematik", 3.8);
        coefficients.put("Fen", 3.3);
        coefficients.put("Sosyal", 3.0);
    }

    public double calculateNet(int correct, int wrong) {
        return correct - (wrong / 4.0);
    }

    public double calculateEstimatedScore(MockExam exam) {
        double total = 100.0;
        for (LessonResult result : exam.getLessonResults()) {
            double net = calculateNet(result.getCorrect(), result.getWrong());
            double coeff = coefficients.getOrDefault(normalizeLessonName(result.getLessonName()), 3.0);
            total += net * coeff;
        }
        return Math.max(0.0, total);
    }

    private String normalizeLessonName(String lessonName) {
        if (lessonName == null) {
            return "";
        }
        return switch (lessonName.trim()) {
            case "Turkce" -> "Türkçe";
            default -> lessonName.trim();
        };
    }
}
