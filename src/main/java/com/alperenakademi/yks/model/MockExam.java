package com.alperenakademi.yks.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MockExam implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private LocalDate date;
    private ExamType examType;
    private List<LessonResult> lessonResults = new ArrayList<>();
    private double estimatedScore;

    public LocalDate getDate() { return date; }
    public ExamType getExamType() { return examType; }
    public List<LessonResult> getLessonResults() { return lessonResults; }
    public double getEstimatedScore() { return estimatedScore; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setExamType(ExamType examType) { this.examType = examType; }
    public void setLessonResults(List<LessonResult> lessonResults) { this.lessonResults = lessonResults; }
    public void setEstimatedScore(double estimatedScore) { this.estimatedScore = estimatedScore; }
}
