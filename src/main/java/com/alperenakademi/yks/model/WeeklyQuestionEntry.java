package com.alperenakademi.yks.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class WeeklyQuestionEntry implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;
    private LocalDate weekStart;
    private String lesson;
    private String topic;
    private int questionCount;
    private int correctCount;
    private int wrongCount;

    public LocalDate getWeekStart() { return weekStart; }
    public void setWeekStart(LocalDate weekStart) { this.weekStart = weekStart; }
    public String getLesson() { return lesson; }
    public void setLesson(String lesson) { this.lesson = lesson; }
    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }
    public int getQuestionCount() { return questionCount; }
    public void setQuestionCount(int questionCount) { this.questionCount = questionCount; }
    public int getCorrectCount() { return correctCount; }
    public void setCorrectCount(int correctCount) { this.correctCount = correctCount; }
    public int getWrongCount() { return wrongCount; }
    public void setWrongCount(int wrongCount) { this.wrongCount = wrongCount; }
}
