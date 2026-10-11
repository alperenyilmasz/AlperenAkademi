package com.alperenakademi.yks.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserAccount implements Serializable {
    @Serial
    private static final long serialVersionUID = 3L;

    private String username;
    private String password;
    private String fullName;
    private String email;
    private String targetUniversity;
    private String targetDepartment;
    private double requiredMathNet;
    private double requiredTytNet;
    private double requiredAytNet;
    private double requiredAverageScore;
    /** Atlas referanslı hedef programa ait yaklaşık yerleşme sırası (küçük daha iyi). */
    private long requiredRanking;
    /** Ortaöğretim başarı puanı kullanıcı girişi: 1-100. */
    private int obp = 50;
    private List<MockExam> mockExams = new ArrayList<>();
    private List<WeeklyQuestionEntry> weeklyQuestions = new ArrayList<>();

    public UserAccount() {
    }

    public UserAccount(String username, String password, String fullName, String email,
                       String targetUniversity, String targetDepartment, double requiredMathNet,
                       double requiredTytNet, double requiredAytNet, double requiredAverageScore,
                       long requiredRanking, int obp) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.targetUniversity = targetUniversity;
        this.targetDepartment = targetDepartment;
        this.requiredMathNet = requiredMathNet;
        this.requiredTytNet = requiredTytNet;
        this.requiredAytNet = requiredAytNet;
        this.requiredAverageScore = requiredAverageScore;
        this.requiredRanking = requiredRanking;
        this.obp = obp;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTargetUniversity() { return targetUniversity; }
    public void setTargetUniversity(String targetUniversity) { this.targetUniversity = targetUniversity; }
    public String getTargetDepartment() { return targetDepartment; }
    public void setTargetDepartment(String targetDepartment) { this.targetDepartment = targetDepartment; }
    public double getRequiredMathNet() { return requiredMathNet; }
    public void setRequiredMathNet(double requiredMathNet) { this.requiredMathNet = requiredMathNet; }
    public double getRequiredTytNet() { return requiredTytNet; }
    public void setRequiredTytNet(double requiredTytNet) { this.requiredTytNet = requiredTytNet; }
    public double getRequiredAytNet() { return requiredAytNet; }
    public void setRequiredAytNet(double requiredAytNet) { this.requiredAytNet = requiredAytNet; }
    public double getRequiredAverageScore() { return requiredAverageScore; }
    public void setRequiredAverageScore(double requiredAverageScore) { this.requiredAverageScore = requiredAverageScore; }
    public long getRequiredRanking() { return requiredRanking; }
    public void setRequiredRanking(long requiredRanking) { this.requiredRanking = requiredRanking; }
    public int getObp() { return obp; }
    public void setObp(int obp) { this.obp = obp; }
    public List<MockExam> getMockExams() { return mockExams; }
    public List<WeeklyQuestionEntry> getWeeklyQuestions() { return weeklyQuestions; }
}
