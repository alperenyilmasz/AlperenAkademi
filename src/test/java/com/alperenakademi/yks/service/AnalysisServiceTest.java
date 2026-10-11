package com.alperenakademi.yks.service;

import com.alperenakademi.yks.model.ExamType;
import com.alperenakademi.yks.model.MockExam;
import com.alperenakademi.yks.model.TargetGoal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

class AnalysisServiceTest {
    private final AnalysisService service = new AnalysisService();

    @Test
    void computesAverageScoreFromLatestExams() {
        MockExam e1 = new MockExam();
        e1.setDate(LocalDate.now().minusDays(1));
        e1.setExamType(ExamType.TYT);
        e1.setEstimatedScore(300);
        MockExam e2 = new MockExam();
        e2.setDate(LocalDate.now());
        e2.setExamType(ExamType.TYT);
        e2.setEstimatedScore(340);
        double avg = service.averageScore(List.of(e1, e2), 2);
        Assertions.assertEquals(320, avg);
    }

    @Test
    void returnsFeedbackForGap() {
        TargetGoal goal = new TargetGoal();
        goal.setDepartmentName("Bilgisayar Muhendisligi");
        goal.setRequiredAverageScore(420);
        goal.setRequiredMathNet(30);
        String feedback = service.goalFeedback(goal, List.of());
        Assertions.assertTrue(feedback.contains("farki"));
    }
}
