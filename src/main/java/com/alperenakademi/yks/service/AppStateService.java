package com.alperenakademi.yks.service;

import com.alperenakademi.yks.model.*;
import com.alperenakademi.yks.repository.AppDataRepository;
import com.alperenakademi.yks.repository.SerializedAppDataRepository;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AppStateService {
    private final AppDataRepository repository;
    private final ScoreCalculatorService calculatorService = new ScoreCalculatorService();
    private final AnalysisService analysisService = new AnalysisService();
    private final WebFeedService webFeedService = new WebFeedService();
    private AppData appData;
    private String activeUsername;

    public AppStateService() throws IOException {
        this.repository = new SerializedAppDataRepository();
        this.appData = repository.load();
    }

    public void addExam(MockExam exam) throws IOException {
        exam.setEstimatedScore(calculatorService.calculateEstimatedScore(exam));
        Optional<UserAccount> active = activeUser();
        if (active.isEmpty()) {
            return;
        }
        active.get().getMockExams().add(exam);
        repository.save(appData);
    }

    public void addWeeklyQuestion(WeeklyQuestionEntry entry) throws IOException {
        Optional<UserAccount> active = activeUser();
        if (active.isEmpty()) {
            return;
        }
        active.get().getWeeklyQuestions().add(entry);
        repository.save(appData);
    }

    public void addGoal(TargetGoal goal) throws IOException {
        appData.getTargetGoals().add(goal);
        repository.save(appData);
    }

    public void addCalendarEvent(ExamCalendarEvent event) throws IOException {
        appData.getCalendarEvents().add(event);
        repository.save(appData);
    }

    public void setYksDate(java.time.LocalDate date) throws IOException {
        appData.setYksDate(date);
        repository.save(appData);
    }

    public void savePreferences(String themeMode, String language) throws IOException {
        appData.setThemeMode(themeMode);
        appData.setLanguage(language);
        repository.save(appData);
    }

    public void saveProfile(String studentName, String schoolName, String grade, String city, String targetDepartment) throws IOException {
        appData.setStudentName(studentName);
        appData.setSchoolName(schoolName);
        appData.setGrade(grade);
        appData.setCity(city);
        appData.setTargetDepartment(targetDepartment);
        repository.save(appData);
    }

    public boolean registerUser(String username, String password, String fullName, String email,
                                String targetUniversity, String targetDepartment,
                                double requiredMathNet, double requiredTytNet, double requiredAytNet,
                                double requiredAverageScore, long requiredRanking, int obp) throws IOException {
        if (username == null || username.isBlank() || password == null || password.isBlank() || email == null || email.isBlank()) {
            return false;
        }
        boolean exists = appData.getUsers().stream()
                .anyMatch(u -> u.getUsername().equalsIgnoreCase(username.trim()));
        if (exists) {
            return false;
        }
        appData.getUsers().add(new UserAccount(
                username.trim(),
                password,
                fullName == null ? "" : fullName.trim(),
                email.trim(),
                normalizeDisplayText(targetUniversity),
                normalizeDisplayText(targetDepartment),
                requiredMathNet,
                requiredTytNet,
                requiredAytNet,
                requiredAverageScore,
                requiredRanking,
                obp
        ));
        repository.save(appData);
        return true;
    }

    public boolean authenticateUser(String username, String password) {
        boolean ok = appData.getUsers().stream()
                .anyMatch(u -> u.getUsername().equalsIgnoreCase(username == null ? "" : username.trim())
                        && u.getPassword().equals(password == null ? "" : password));
        if (ok) {
            setActiveUser(username);
        }
        return ok;
    }

    public void setActiveUser(String username) {
        this.activeUsername = username == null ? null : username.trim();
    }

    public void clearActiveUser() {
        this.activeUsername = null;
    }

    public String fullNameOfUser(String username) {
        Optional<UserAccount> user = appData.getUsers().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username == null ? "" : username.trim()))
                .findFirst();
        return user.map(UserAccount::getFullName).orElse(username == null ? "" : username);
    }

    public String emailOfUser(String username) {
        Optional<UserAccount> user = appData.getUsers().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username == null ? "" : username.trim()))
                .findFirst();
        return user.map(UserAccount::getEmail).orElse("");
    }

    public boolean updateUserProfile(String username, String fullName, String email,
                                     String targetUniversity, String targetDepartment,
                                     double requiredMathNet, double requiredTytNet, double requiredAytNet,
                                     double requiredAverageScore, long requiredRanking, int obp) throws IOException {
        Optional<UserAccount> user = appData.getUsers().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username == null ? "" : username.trim()))
                .findFirst();
        if (user.isEmpty()) return false;
        user.get().setFullName(fullName == null ? "" : fullName.trim());
        user.get().setEmail(email == null ? "" : email.trim());
        user.get().setTargetUniversity(normalizeDisplayText(targetUniversity));
        user.get().setTargetDepartment(normalizeDisplayText(targetDepartment));
        user.get().setRequiredMathNet(requiredMathNet);
        user.get().setRequiredTytNet(requiredTytNet);
        user.get().setRequiredAytNet(requiredAytNet);
        user.get().setRequiredAverageScore(requiredAverageScore);
        user.get().setRequiredRanking(requiredRanking);
        user.get().setObp(obp);
        repository.save(appData);
        return true;
    }

    public String targetUniversityOfUser(String username) {
        Optional<UserAccount> user = appData.getUsers().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username == null ? "" : username.trim()))
                .findFirst();
        return user.map(UserAccount::getTargetUniversity).map(this::normalizeDisplayText).orElse("");
    }

    public String targetDepartmentOfUser(String username) {
        Optional<UserAccount> user = appData.getUsers().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username == null ? "" : username.trim()))
                .findFirst();
        return user.map(UserAccount::getTargetDepartment).map(this::normalizeDisplayText).orElse("");
    }

    public double requiredMathNetOfUser(String username) {
        Optional<UserAccount> user = appData.getUsers().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username == null ? "" : username.trim()))
                .findFirst();
        return user.map(UserAccount::getRequiredMathNet).orElse(0.0);
    }

    public double requiredTytNetOfUser(String username) {
        Optional<UserAccount> user = appData.getUsers().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username == null ? "" : username.trim()))
                .findFirst();
        return user.map(UserAccount::getRequiredTytNet).orElse(0.0);
    }

    public double requiredAytNetOfUser(String username) {
        Optional<UserAccount> user = appData.getUsers().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username == null ? "" : username.trim()))
                .findFirst();
        return user.map(UserAccount::getRequiredAytNet).orElse(0.0);
    }

    public double requiredAverageScoreOfUser(String username) {
        Optional<UserAccount> user = appData.getUsers().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username == null ? "" : username.trim()))
                .findFirst();
        return user.map(UserAccount::getRequiredAverageScore).orElse(0.0);
    }

    public long requiredRankingOfUser(String username) {
        Optional<UserAccount> user = appData.getUsers().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username == null ? "" : username.trim()))
                .findFirst();
        return user.map(UserAccount::getRequiredRanking).orElse(0L);
    }

    public int obpOfUser(String username) {
        Optional<UserAccount> user = appData.getUsers().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username == null ? "" : username.trim()))
                .findFirst();
        int v = user.map(UserAccount::getObp).orElse(50);
        if (v < 1 || v > 100) {
            return 50;
        }
        return v;
    }

    public boolean hasRegisteredUsers() {
        return !appData.getUsers().isEmpty();
    }

    public String studentName() { return appData.getStudentName(); }
    public String schoolName() { return appData.getSchoolName(); }
    public String grade() { return appData.getGrade(); }
    public String city() { return appData.getCity(); }
    public String targetDepartment() { return appData.getTargetDepartment(); }
    public boolean isProfileCompleted() {
        return appData.getStudentName() != null && !appData.getStudentName().isBlank();
    }

    public List<MockExam> exams() {
        return activeUser().map(UserAccount::getMockExams).orElse(Collections.emptyList());
    }
    public List<WeeklyQuestionEntry> weeklyQuestions() {
        return activeUser().map(UserAccount::getWeeklyQuestions).orElse(Collections.emptyList());
    }
    public List<TargetGoal> goals() { return appData.getTargetGoals(); }
    public List<ExamCalendarEvent> events() { return appData.getCalendarEvents(); }
    public java.time.LocalDate yksDate() { return appData.getYksDate(); }
    public String themeMode() { return appData.getThemeMode(); }
    public String language() { return appData.getLanguage(); }
    public List<NewsItem> news() { return webFeedService.loadYksNews(); }
    public List<ExamCalendarEvent> turkeyGeneralExamCalendar() { return webFeedService.loadTurkeyGeneralExams(); }
    public double averageScore(int limit) { return analysisService.averageScore(exams(), limit); }
    public String goalFeedback(TargetGoal goal) { return analysisService.goalFeedback(goal, exams()); }

    private Optional<UserAccount> activeUser() {
        if (activeUsername == null || activeUsername.isBlank()) {
            return Optional.empty();
        }
        return appData.getUsers().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(activeUsername))
                .findFirst();
    }

    private String normalizeDisplayText(String text) {
        if (text == null) {
            return "";
        }
        return switch (text.trim()) {
            case "Bogazici Universitesi" -> "Boğaziçi Üniversitesi";
            case "Hacettepe Universitesi" -> "Hacettepe Üniversitesi";
            case "ODTU" -> "ODTÜ";
            case "ITU" -> "İTÜ";
            case "Bilgisayar Muhendisligi" -> "Bilgisayar Mühendisliği";
            case "Elektrik-Elektronik Muhendisligi" -> "Elektrik-Elektronik Mühendisliği";
            case "Makine Muhendisligi" -> "Makine Mühendisliği";
            case "Yazilim Muhendisligi" -> "Yazılım Mühendisliği";
            case "Insaat Muhendisligi" -> "İnşaat Mühendisliği";
            case "Tip" -> "Tıp";
            case "Dis Hekimligi" -> "Diş Hekimliği";
            default -> text.trim();
        };
    }
}
