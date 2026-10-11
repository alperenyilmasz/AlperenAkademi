package com.alperenakademi.yks.service;

import com.alperenakademi.yks.model.LessonResult;
import com.alperenakademi.yks.model.MockExam;
import com.alperenakademi.yks.model.TargetGoal;

import java.util.Comparator;
import java.util.List;

public class AnalysisService {
    public double averageScore(List<MockExam> exams, int limit) {
        return exams.stream()
                .sorted(Comparator.comparing(MockExam::getDate).reversed())
                .limit(limit)
                .mapToDouble(MockExam::getEstimatedScore)
                .average()
                .orElse(0.0);
    }

    public String goalFeedback(TargetGoal goal, List<MockExam> exams) {
        double avg = averageScore(exams, 10);
        double diff = goal.getRequiredAverageScore() - avg;
        double mathNetAvg = exams.stream()
                .flatMap(e -> e.getLessonResults().stream())
                .filter(r -> "Matematik".equalsIgnoreCase(r.getLessonName()))
                .mapToDouble(this::netOf)
                .average()
                .orElse(0.0);
        double mathDiff = goal.getRequiredMathNet() - mathNetAvg;
        if (diff <= 0 && mathDiff <= 0) {
            return "Hedef seviyedesin. Mevcut tempoyu koru.";
        }
        return String.format("Hedefe yaklaşmak için puan farkı: %.1f, Matematik net farkı: %.1f", diff, mathDiff);
    }

    private double netOf(LessonResult result) {
        return result.getCorrect() - result.getWrong() / 4.0;
    }
}
