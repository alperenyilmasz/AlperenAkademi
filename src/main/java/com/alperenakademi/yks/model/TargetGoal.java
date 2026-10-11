package com.alperenakademi.yks.model;

import java.io.Serial;
import java.io.Serializable;

public class TargetGoal implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String departmentName;
    private double requiredAverageScore;
    private double requiredMathNet;

    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
    public double getRequiredAverageScore() { return requiredAverageScore; }
    public void setRequiredAverageScore(double requiredAverageScore) { this.requiredAverageScore = requiredAverageScore; }
    public double getRequiredMathNet() { return requiredMathNet; }
    public void setRequiredMathNet(double requiredMathNet) { this.requiredMathNet = requiredMathNet; }
}
