package com.alperenakademi.yks.service;

import com.alperenakademi.yks.model.ExamType;
import com.alperenakademi.yks.model.LessonResult;
import com.alperenakademi.yks.model.MockExam;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

class ScoreCalculatorServiceTest {
    private final ScoreCalculatorService service = new ScoreCalculatorService();

    @Test
    void calculatesNetWithFourWrongRule() {
        double net = service.calculateNet(20, 8);
        Assertions.assertEquals(18.0, net);
    }

    @Test
    void calculatesEstimatedScore() {
        MockExam exam = new MockExam();
        exam.setDate(LocalDate.now());
        exam.setExamType(ExamType.TYT);
        exam.setLessonResults(List.of(new LessonResult("Matematik", 20, 8)));
        double score = service.calculateEstimatedScore(exam);
        Assertions.assertTrue(score > 150);
    }
}
