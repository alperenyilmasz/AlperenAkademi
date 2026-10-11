package com.alperenakademi.yks.model;

import java.io.Serial;
import java.io.Serializable;

public class LessonResult implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String lessonName;
    private int correct;
    private int wrong;

    public LessonResult() {
    }

    public LessonResult(String lessonName, int correct, int wrong) {
        this.lessonName = lessonName;
        this.correct = correct;
        this.wrong = wrong;
    }

    public String getLessonName() { return lessonName; }
    public int getCorrect() { return correct; }
    public int getWrong() { return wrong; }
    public void setLessonName(String lessonName) { this.lessonName = lessonName; }
    public void setCorrect(int correct) { this.correct = correct; }
    public void setWrong(int wrong) { this.wrong = wrong; }
}
