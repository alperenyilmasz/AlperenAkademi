package com.alperenakademi.yks.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AppData implements Serializable {
    @Serial
    private static final long serialVersionUID = 5L;
    private LocalDate yksDate = LocalDate.of(2026, 6, 20);
    private List<MockExam> mockExams = new ArrayList<>();
    private List<WeeklyQuestionEntry> weeklyQuestions = new ArrayList<>();
    private List<TargetGoal> targetGoals = new ArrayList<>();
    private List<ExamCalendarEvent> calendarEvents = new ArrayList<>();
    private String studentName = "";
    private String schoolName = "";
    private String grade = "";
    private String city = "";
    private String targetDepartment = "";
    private String themeMode = "dark";
    private String language = "tr";
    private List<UserAccount> users = new ArrayList<>();

    public LocalDate getYksDate() { return yksDate; }
    public void setYksDate(LocalDate yksDate) { this.yksDate = yksDate; }
    public List<MockExam> getMockExams() { return mockExams; }
    public List<WeeklyQuestionEntry> getWeeklyQuestions() { return weeklyQuestions; }
    public List<TargetGoal> getTargetGoals() { return targetGoals; }
    public List<ExamCalendarEvent> getCalendarEvents() { return calendarEvents; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public String getSchoolName() { return schoolName; }
    public void setSchoolName(String schoolName) { this.schoolName = schoolName; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getTargetDepartment() { return targetDepartment; }
    public void setTargetDepartment(String targetDepartment) { this.targetDepartment = targetDepartment; }
    public String getThemeMode() { return themeMode; }
    public void setThemeMode(String themeMode) { this.themeMode = themeMode; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public List<UserAccount> getUsers() { return users; }
}
