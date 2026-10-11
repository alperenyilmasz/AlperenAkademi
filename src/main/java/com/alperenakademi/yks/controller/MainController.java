package com.alperenakademi.yks.controller;

import com.alperenakademi.yks.model.*;
import com.alperenakademi.yks.service.AppStateService;
import com.alperenakademi.yks.service.CountdownService;
import com.alperenakademi.yks.ui.countdown.YksCountdownRingsPanel;
import com.alperenakademi.yks.service.ExamInputLimits;
import com.alperenakademi.yks.service.PlacementProxyService;
import com.alperenakademi.yks.service.RankingEstimator;
import com.alperenakademi.yks.service.ScoreCalculatorService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.*;

public class MainController {
    @FXML private BorderPane rootPane;
    @FXML private HBox appContentContainer;
    @FXML private VBox authContainer;
    @FXML private Label authTitleLabel;
    @FXML private Label authSubtitleLabel;
    @FXML private VBox loginFormPane;
    @FXML private VBox registerFormPane;
    @FXML private Label loginFormHeadingLabel;
    @FXML private TextField loginUsernameField;
    @FXML private PasswordField loginPasswordField;
    @FXML private Button loginButton;
    @FXML private Label loginStatusLabel;
    @FXML private Button showRegisterButton;
    @FXML private Label registerFormHeadingLabel;
    @FXML private TextField registerFullNameField;
    @FXML private TextField registerEmailField;
    @FXML private TextField registerUsernameField;
    @FXML private PasswordField registerPasswordField;
    @FXML private TextField registerObpField;
    @FXML private Label registerObpHintLabel;
    @FXML private Label registerSectionTargetLabel;
    @FXML private ComboBox<String> registerUniversityCombo;
    @FXML private ComboBox<String> registerDepartmentCombo;
    @FXML private Button registerButton;
    @FXML private Label registerStatusLabel;
    @FXML private Button showLoginButton;
    @FXML private TabPane mainTabPane;
    @FXML private ToggleButton sidebarDashboardButton;
    @FXML private ToggleButton sidebarExamButton;
    @FXML private ToggleButton sidebarExamListButton;
    @FXML private ToggleButton sidebarAnalysisButton;
    @FXML private ToggleButton sidebarWeeklyButton;
    @FXML private ToggleButton sidebarGoalButton;
    @FXML private ToggleButton sidebarCalendarButton;
    @FXML private ToggleButton sidebarNewsButton;
    @FXML private ToggleButton sidebarSettingsButton;
    @FXML private ToggleButton logoutButton;
    @FXML private Tab dashboardTab;
    @FXML private Tab examTab;
    @FXML private Tab examListTab;
    @FXML private Label examListIntroLabel;
    @FXML private Label tytExamListTitleLabel;
    @FXML private Label aytExamListTitleLabel;
    @FXML private VBox tytExamsCardsContainer;
    @FXML private VBox aytExamsCardsContainer;
    @FXML private Tab analysisTab;
    @FXML private Tab weeklyTab;
    @FXML private Tab goalTab;
    @FXML private Tab calendarTab;
    @FXML private Tab newsTab;
    @FXML private Tab settingsTab;
    @FXML private Label appTitleLabel;
    @FXML private Label appSubtitleLabel;
    @FXML private Label welcomeLabel;
    @FXML private VBox sidebar;
    @FXML private VBox countdownCard;
    @FXML private StackPane countdownRingsHost;
    @FXML private VBox dashboardMetricsCard;
    @FXML private HBox dashboardMetricsRow;
    @FXML private VBox performanceSummaryColumn;
    @FXML private Region dashboardMetricDivider;
    @FXML private VBox targetProgressColumn;
    @FXML private Label avg5Label;
    @FXML private Label targetProgressHeadingLabel;
    @FXML private VBox targetDetailsCard;
    @FXML private Label targetInfoLabel;
    @FXML private Label targetRemainingLabel;
    @FXML private Label targetStatusLabel;
    @FXML private VBox dashboardNewsContainer;
    @FXML private VBox dashboardExamsContainer;
    @FXML private DatePicker examDatePicker;
    @FXML private ComboBox<ExamType> examTypeCombo;
    @FXML private VBox examLessonsContainer;
    @FXML private Label examStatusLabel;
    @FXML private Label examDateHintLabel;
    @FXML private LineChart<String, Number> tytScoreLineChart;
    @FXML private LineChart<String, Number> aytScoreLineChart;
    @FXML private BarChart<String, Number> tytLessonBarChart;
    @FXML private BarChart<String, Number> aytLessonBarChart;
    @FXML private ComboBox<String> weeklyLessonCombo;
    @FXML private ComboBox<String> weeklyTopicCombo;
    @FXML private TextField weeklyCountField;
    @FXML private TextField weeklyCorrectField;
    @FXML private TextField weeklyWrongField;
    @FXML private VBox weeklyQuestionCardsContainer;
    @FXML private Label goalAnalysisHeadingLabel;
    @FXML private Label goalInfoLabel;
    @FXML private Label goalLastExamScoreHeading;
    @FXML private Label goalLastExamRankHeading;
    @FXML private Label goalLastExamScoreLabel;
    @FXML private Label goalLastExamRankLabel;
    @FXML private Label goalTargetScoreHeading;
    @FXML private Label goalTargetRankHeading;
    @FXML private Label goalTargetAverageScoreLabel;
    @FXML private Label goalTargetRankingLabel;
    @FXML private Label goalPlacementBreakdownLabel;
    @FXML private Label goalRemainingLabel;
    @FXML private Label goalStatusLabel;
    @FXML private VBox goalTargetStatsContainer;
    @FXML private VBox goalLessonBreakdownContainer;
    @FXML private VBox eventCardsContainer;
    @FXML private VBox newsCardsContainer;
    @FXML private Label calendarTitleLabel;
    @FXML private Label calendarInfoLabel;
    @FXML private Button refreshCalendarButton;
    @FXML private Label newsTitleLabel;
    @FXML private Label settingsAvatarLabel;
    @FXML private Label settingsUserNameLabel;
    @FXML private ToggleButton settingsNavProfileButton;
    @FXML private ToggleButton settingsNavAppearanceButton;
    @FXML private VBox settingsProfilePane;
    @FXML private VBox settingsAppearancePane;
    @FXML private Label settingsContentTitleLabel;
    @FXML private Label settingsGroupProfileLabel;
    @FXML private Label settingsGroupContactLabel;
    @FXML private Label settingsNameLabel;
    @FXML private TextField settingsNameField;
    @FXML private Label settingsUsernameLabel;
    @FXML private TextField settingsUsernameDisplayField;
    @FXML private Label settingsObpLabel;
    @FXML private TextField settingsObpField;
    @FXML private Label settingsEmailLabel;
    @FXML private TextField settingsEmailField;
    @FXML private Label settingsUniversityLabel;
    @FXML private ComboBox<String> settingsUniversityCombo;
    @FXML private Label settingsDepartmentLabel;
    @FXML private ComboBox<String> settingsDepartmentCombo;
    @FXML private Label settingsAppearanceTitleLabel;
    @FXML private Label settingsAppearanceGroupLabel;
    @FXML private Button updateProfileButton;
    @FXML private Label settingsProfileStatusLabel;
    @FXML private Label themeLabel;
    @FXML private ComboBox<String> themeCombo;
    @FXML private Label languageLabel;
    @FXML private ComboBox<String> languageCombo;
    @FXML private Button saveSettingsButton;

    private final CountdownService countdownService = new CountdownService();
    private final ScoreCalculatorService scoreCalculatorService = new ScoreCalculatorService();
    private final Map<String, TextField> lessonCorrectFields = new LinkedHashMap<>();
    private final Map<String, TextField> lessonWrongFields = new LinkedHashMap<>();
    private final Map<String, List<String>> weeklyTopicsByLesson = createWeeklyTopicsMap();
    private final Map<String, Map<String, TargetRequirement>> targetMathNetMap = createTargetMathNetMap();
    private AppStateService state;
    private String activeUsername;
    private YksCountdownRingsPanel yksCountdownRingsPanel;
    private final Map<Tab, ToggleButton> sidebarNavButtons = new LinkedHashMap<>();
    private boolean sidebarCollapsed;
    private VBox dashboardMetricsStack;
    private boolean dashboardMetricsStacked;

    public void initialize(AppStateService appStateService) {
        this.state = appStateService;
        examTypeCombo.setItems(FXCollections.observableArrayList(ExamType.values()));
        examTypeCombo.valueProperty().addListener((obs, oldValue, newValue) -> refreshLessonsByExamType(newValue));
        examTypeCombo.setValue(ExamType.TYT);
        targetStatusLabel.setWrapText(true);
        goalStatusLabel.setWrapText(true);
        initializeWeeklyTrackingSelections();
        initializeTargetSelection();
        initializePreferences();
        initializeSidebarNavigation();
        initializeSettingsNavigation();
        initializeSidebarResponsive();
        yksCountdownRingsPanel = new YksCountdownRingsPanel(isEnglishUi());
        countdownRingsHost.getChildren().setAll(yksCountdownRingsPanel);
        initializeDashboardMetricsLayout();
        refreshAll();
        showAuthScreen(true);
        if (state.hasRegisteredUsers()) {
            showAuthLoginForm();
        } else {
            showAuthRegisterForm();
        }
    }

    @FXML
    private void onShowLoginForm() {
        showAuthLoginForm();
    }

    @FXML
    private void onShowRegisterForm() {
        showAuthRegisterForm();
    }

    @FXML
    private void onLogout() {
        logoutButton.setSelected(false);
        countdownService.stop();
        activeUsername = null;
        state.clearActiveUser();
        loginPasswordField.clear();
        loginUsernameField.clear();
        welcomeLabel.setText(isEnglishUi() ? "Welcome" : "Hoş geldin");
        setAuthStatus(loginStatusLabel, "", true);
        showAuthLoginForm();
        showAuthScreen(true);
    }

    @FXML
    private void onLogin() {
        if (state.authenticateUser(loginUsernameField.getText(), loginPasswordField.getText())) {
            activeUsername = loginUsernameField.getText().trim();
            state.setActiveUser(activeUsername);
            setAuthStatus(loginStatusLabel, isEnglishUi() ? "Sign-in successful." : "Giriş başarılı.", true);
            boolean en = isEnglishUi();
            welcomeLabel.setText((en ? "Welcome, " : "Hoş geldin, ") + state.fullNameOfUser(activeUsername));
            refreshSettingsProfile();
            showAuthScreen(false);
            startCountdown();
            mainTabPane.getSelectionModel().select(0);
        } else {
            setAuthStatus(loginStatusLabel,
                    isEnglishUi() ? "Invalid username or password." : "Kullanıcı adı veya şifre hatalı.",
                    false);
        }
    }

    @FXML
    private void onRegister() {
        try {
            Optional<Integer> obpParsed = parseObp1To100(registerObpField.getText());
            if (obpParsed.isEmpty()) {
                setAuthStatus(registerStatusLabel, isEnglishUi()
                        ? "OBP is required (integer 1-100)."
                        : "OBP zorunludur ve 1-100 arasında tam sayı olmalıdır.", false);
                return;
            }
            boolean ok = state.registerUser(
                    registerUsernameField.getText(),
                    registerPasswordField.getText(),
                    registerFullNameField.getText(),
                    registerEmailField.getText(),
                    registerUniversityCombo.getValue(),
                    registerDepartmentCombo.getValue(),
                    selectedRequiredMathNet(registerUniversityCombo.getValue(), registerDepartmentCombo.getValue()),
                    selectedRequiredTytNet(registerUniversityCombo.getValue(), registerDepartmentCombo.getValue()),
                    selectedRequiredAytNet(registerUniversityCombo.getValue(), registerDepartmentCombo.getValue()),
                    selectedRequiredAverageScore(registerUniversityCombo.getValue(), registerDepartmentCombo.getValue()),
                    selectedRequiredRanking(registerUniversityCombo.getValue(), registerDepartmentCombo.getValue()),
                    obpParsed.get()
            );
            setAuthStatus(registerStatusLabel, ok
                    ? (isEnglishUi() ? "Registration successful. You can sign in now." : "Kayıt başarılı. Şimdi giriş yapın.")
                    : (isEnglishUi() ? "Registration failed. Username may already exist." : "Kayıt başarısız. Kullanıcı adı dolu olabilir."),
                    ok);
            if (ok) showAuthLoginForm();
        } catch (IOException e) {
            setAuthStatus(registerStatusLabel, (isEnglishUi() ? "Error: " : "Hata: ") + e.getMessage(), false);
        }
    }

    @FXML
    private void onUpdateProfile() {
        if (activeUsername == null || activeUsername.isBlank()) return;
        try {
            Optional<Integer> obpParsed = parseObp1To100(settingsObpField.getText());
            if (obpParsed.isEmpty()) {
                settingsProfileStatusLabel.setText(isEnglishUi()
                        ? "OBP must be an integer between 1 and 100."
                        : "OBP 1-100 arasında tam sayı olmalıdır.");
                return;
            }
            boolean ok = state.updateUserProfile(
                    activeUsername,
                    settingsNameField.getText(),
                    settingsEmailField.getText(),
                    settingsUniversityCombo.getValue(),
                    settingsDepartmentCombo.getValue(),
                    selectedRequiredMathNet(settingsUniversityCombo.getValue(), settingsDepartmentCombo.getValue()),
                    selectedRequiredTytNet(settingsUniversityCombo.getValue(), settingsDepartmentCombo.getValue()),
                    selectedRequiredAytNet(settingsUniversityCombo.getValue(), settingsDepartmentCombo.getValue()),
                    selectedRequiredAverageScore(settingsUniversityCombo.getValue(), settingsDepartmentCombo.getValue()),
                    selectedRequiredRanking(settingsUniversityCombo.getValue(), settingsDepartmentCombo.getValue()),
                    obpParsed.get()
            );
            settingsProfileStatusLabel.setText(ok ? "Bilgiler güncellendi." : "Güncelleme başarısız.");
            if (ok) {
                welcomeLabel.setText("Hoş geldin, " + state.fullNameOfUser(activeUsername));
                refreshTargetProgress();
            }
        } catch (IOException e) {
            settingsProfileStatusLabel.setText("Hata: " + e.getMessage());
        }
    }

    @FXML
    private void onSaveExam() {
        try {
            if (examDatePicker.getValue() == null) {
                examStatusLabel.setText(isEnglishUi()
                        ? "Exam date is required. Please select a date before saving."
                        : "Deneme tarihi seçmek zorunludur. Kaydetmeden önce lütfen tarih seçin.");
                return;
            }
            MockExam exam = new MockExam();
            exam.setDate(examDatePicker.getValue());
            ExamType examType = examTypeCombo.getValue() == null ? ExamType.TYT : examTypeCombo.getValue();
            exam.setExamType(examType);
            List<LessonResult> lessonResults = new ArrayList<>();
            for (String lessonName : lessonCorrectFields.keySet()) {
                int correct;
                int wrong;
                try {
                    correct = parseExamPositiveIntField(lessonCorrectFields.get(lessonName), lessonName + " doğru");
                    wrong = parseExamPositiveIntField(lessonWrongFields.get(lessonName), lessonName + " yanlış");
                } catch (NumberFormatException nf) {
                    examStatusLabel.setText(nf.getMessage());
                    return;
                }
                String limitError = ExamInputLimits.validationErrorTurkish(examType, lessonName, correct, wrong);
                if (limitError != null) {
                    examStatusLabel.setText(limitError + " Kayıt iptal.");
                    return;
                }
                lessonResults.add(new LessonResult(lessonName, correct, wrong));
            }
            exam.setLessonResults(lessonResults);
            exam.setEstimatedScore(scoreCalculatorService.calculateEstimatedScore(exam));
            state.addExam(exam);
            examStatusLabel.setText("Deneme kaydedildi. Tahmini puan: " + String.format("%.2f", exam.getEstimatedScore()));
            refreshAll();
        } catch (Exception ex) {
            examStatusLabel.setText("Hata: " + ex.getMessage());
        }
    }

    @FXML
    private void onAddWeeklyQuestion() {
        try {
            WeeklyQuestionEntry entry = new WeeklyQuestionEntry();
            entry.setWeekStart(LocalDate.now().minusDays(LocalDate.now().getDayOfWeek().getValue() - 1L));
            entry.setLesson(weeklyLessonCombo.getValue());
            entry.setTopic(weeklyTopicCombo.getValue());
            entry.setQuestionCount(parseIntOrZero(weeklyCountField.getText()));
            entry.setCorrectCount(parseIntOrZero(weeklyCorrectField.getText()));
            entry.setWrongCount(parseIntOrZero(weeklyWrongField.getText()));
            state.addWeeklyQuestion(entry);
            refreshWeekly();
        } catch (Exception ignored) {
        }
    }

    @FXML
    private void onRefreshCalendar() {
        refreshEvents();
    }

    @FXML
    private void onSaveSettings() throws IOException {
        state.savePreferences(themeCombo.getValue(), languageCombo.getValue());
        applyTheme(themeCombo.getValue());
        applyLanguage(languageCombo.getValue());
        refreshExamRecordsList();
        refreshWeekly();
        refreshTargetProgress();
    }

    private void refreshAll() {
        avg5Label.setText("Son 5 Ortalama: " + String.format("%.2f", state.averageScore(5)));
        refreshCharts();
        refreshWeekly();
        refreshExamRecordsList();
        refreshEvents();
        refreshNews();
        refreshDashboardFeeds();
        refreshTargetProgress();
    }

    private void initializeSidebarNavigation() {
        ToggleGroup sidebarGroup = new ToggleGroup();
        sidebarNavButtons.clear();
        sidebarNavButtons.put(dashboardTab, sidebarDashboardButton);
        sidebarNavButtons.put(examTab, sidebarExamButton);
        sidebarNavButtons.put(examListTab, sidebarExamListButton);
        sidebarNavButtons.put(analysisTab, sidebarAnalysisButton);
        sidebarNavButtons.put(weeklyTab, sidebarWeeklyButton);
        sidebarNavButtons.put(goalTab, sidebarGoalButton);
        sidebarNavButtons.put(calendarTab, sidebarCalendarButton);
        sidebarNavButtons.put(newsTab, sidebarNewsButton);
        sidebarNavButtons.put(settingsTab, sidebarSettingsButton);

        sidebarNavButtons.forEach((tab, button) -> {
            button.setToggleGroup(sidebarGroup);
            button.setOnAction(event -> mainTabPane.getSelectionModel().select(tab));
        });
        mainTabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) ->
                updateSidebarSelection(newTab));
        updateSidebarSelection(mainTabPane.getSelectionModel().getSelectedItem());
    }

    private void initializeSidebarResponsive() {
        rootPane.widthProperty().addListener((obs, oldWidth, newWidth) ->
                updateSidebarResponsiveMode(newWidth.doubleValue()));
        updateSidebarResponsiveMode(rootPane.getWidth());
    }

    private void updateSidebarResponsiveMode(double width) {
        boolean shouldCollapse = width > 0 && width < 980;
        if (shouldCollapse == sidebarCollapsed) {
            return;
        }
        sidebarCollapsed = shouldCollapse;
        sidebar.getStyleClass().remove("app-sidebar-collapsed");
        if (sidebarCollapsed) {
            sidebar.getStyleClass().add("app-sidebar-collapsed");
        }
        appSubtitleLabel.setVisible(!sidebarCollapsed);
        appSubtitleLabel.setManaged(!sidebarCollapsed);
        boolean loggedIn = appContentContainer.isVisible();
        welcomeLabel.setVisible(loggedIn && !sidebarCollapsed);
        welcomeLabel.setManaged(loggedIn && !sidebarCollapsed);
        logoutButton.setVisible(loggedIn);
        logoutButton.setManaged(loggedIn);
        updateSidebarLabels(isEnglishUi());
    }

    private void updateSidebarSelection(Tab selectedTab) {
        sidebarNavButtons.forEach((tab, button) -> button.setSelected(tab == selectedTab));
    }

    private void initializeDashboardMetricsLayout() {
        dashboardMetricsCard.widthProperty().addListener((obs, oldWidth, newWidth) ->
                updateDashboardMetricsLayout(newWidth.doubleValue()));
        updateDashboardMetricsLayout(dashboardMetricsCard.getWidth());
    }

    private void updateDashboardMetricsLayout(double width) {
        if (width <= 0) {
            return;
        }

        boolean shouldStack = width < 720;
        if (shouldStack == dashboardMetricsStacked) {
            return;
        }
        dashboardMetricsStacked = shouldStack;

        if (shouldStack) {
            if (dashboardMetricsStack == null) {
                dashboardMetricsStack = new VBox(14);
                dashboardMetricsStack.setFillWidth(true);
                dashboardMetricsStack.getStyleClass().add("dashboard-metrics-stack");
            }
            dashboardMetricsRow.getChildren().clear();
            dashboardMetricDivider.setManaged(false);
            dashboardMetricDivider.setVisible(false);
            targetProgressColumn.setAlignment(Pos.TOP_LEFT);
            setTargetMetricAlignment(TextAlignment.LEFT);
            dashboardMetricsStack.getChildren().setAll(performanceSummaryColumn, targetProgressColumn);
            dashboardMetricsCard.getChildren().setAll(dashboardMetricsStack);
            return;
        }

        if (dashboardMetricsStack != null) {
            dashboardMetricsStack.getChildren().clear();
        }
        dashboardMetricDivider.setManaged(true);
        dashboardMetricDivider.setVisible(true);
        targetProgressColumn.setAlignment(Pos.TOP_RIGHT);
        targetDetailsCard.setAlignment(Pos.TOP_RIGHT);
        setTargetMetricAlignment(TextAlignment.RIGHT);
        dashboardMetricsRow.getChildren().setAll(performanceSummaryColumn, dashboardMetricDivider, targetProgressColumn);
        dashboardMetricsCard.getChildren().setAll(dashboardMetricsRow);
    }

    private void setTargetMetricAlignment(TextAlignment alignment) {
        if (alignment == TextAlignment.LEFT) {
            targetDetailsCard.setAlignment(Pos.TOP_LEFT);
        }
        targetProgressHeadingLabel.setTextAlignment(alignment);
        targetInfoLabel.setTextAlignment(alignment);
        targetRemainingLabel.setTextAlignment(alignment);
        targetStatusLabel.setTextAlignment(alignment);
    }

    private void refreshExamRecordsList() {
        Comparator<MockExam> byDateDesc = (a, b) -> {
            LocalDate da = a.getDate();
            LocalDate db = b.getDate();
            if (da == null && db == null) return 0;
            if (da == null) return 1;
            if (db == null) return -1;
            return db.compareTo(da);
        };
        List<MockExam> tyt = state.exams().stream().filter(e -> e.getExamType() == ExamType.TYT).sorted(byDateDesc).toList();
        List<MockExam> ayt = state.exams().stream().filter(e -> e.getExamType() == ExamType.AYT).sorted(byDateDesc).toList();

        populateExamCardsColumn(tytExamsCardsContainer, tyt, ExamType.TYT);
        populateExamCardsColumn(aytExamsCardsContainer, ayt, ExamType.AYT);
    }

    private void populateExamCardsColumn(VBox container, List<MockExam> exams, ExamType columnType) {
        container.getChildren().clear();
        if (exams.isEmpty()) {
            Label empty = new Label(isEnglishUi()
                    ? (columnType == ExamType.TYT ? "No TYT exams yet." : "No AYT exams yet.")
                    : (columnType == ExamType.TYT ? "Henüz TYT denemesi yok." : "Henüz AYT denemesi yok."));
            empty.getStyleClass().add("metric-note");
            empty.setWrapText(true);
            container.getChildren().add(empty);
            return;
        }
        for (MockExam exam : exams) {
            container.getChildren().add(buildExamRecordCard(exam));
        }
    }

    private VBox buildExamRecordCard(MockExam e) {
        String datePart = e.getDate() != null ? e.getDate().toString() : (isEnglishUi() ? "(no date)" : "(tarih yok)");
        double sumNet = e.getLessonResults().stream()
                .mapToDouble(r -> r.getCorrect() - r.getWrong() / 4.0)
                .sum();
        int lessonCount = e.getLessonResults().size();

        Label dateLabel = new Label(datePart);
        dateLabel.getStyleClass().add("exam-record-card-date");

        ExamType typ = e.getExamType();
        Label typeChip = new Label(typ != null ? typ.name() : "—");
        typeChip.getStyleClass().add("exam-record-chip");

        HBox topRow = new HBox(10, dateLabel, typeChip);
        topRow.setAlignment(Pos.CENTER_LEFT);

        String netLine = isEnglishUi()
                ? String.format("Total net: %.2f", sumNet)
                : String.format("Toplam net: %.2f", sumNet);
        Label netLabel = new Label(netLine);
        netLabel.getStyleClass().add("exam-record-net");

        String scoreLine = isEnglishUi()
                ? String.format("Est. score: %.2f", e.getEstimatedScore())
                : String.format("Tahmini puan: %.2f", e.getEstimatedScore());
        Label scoreLabel = new Label(scoreLine);
        scoreLabel.getStyleClass().add("exam-record-score");

        HBox metricsRow = new HBox(18, netLabel, scoreLabel);
        metricsRow.setAlignment(Pos.CENTER_LEFT);

        String lessonsLine = isEnglishUi()
                ? lessonCount + " lesson(s) recorded"
                : lessonCount + " ders kaydı";
        Label lessonsHint = new Label(lessonsLine);
        lessonsHint.getStyleClass().add("exam-record-meta");

        VBox card = new VBox(8, topRow, metricsRow, lessonsHint);
        card.getStyleClass().addAll("feed-card", "exam-record-card");
        card.setMaxWidth(Double.MAX_VALUE);
        return card;
    }

    private boolean isEnglishUi() {
        return languageCombo != null && "en".equalsIgnoreCase(languageCombo.getValue());
    }

    private void startCountdown() {
        countdownService.start(state.yksDate(), (txt, secondsLeft) -> {
            yksCountdownRingsPanel.update(secondsLeft, state.yksDate());
        });
    }

    private void refreshSettingsProfile() {
        if (activeUsername == null || activeUsername.isBlank()) return;
        String fullName = state.fullNameOfUser(activeUsername);
        settingsNameField.setText(fullName);
        settingsEmailField.setText(state.emailOfUser(activeUsername));
        settingsObpField.setText(String.valueOf(state.obpOfUser(activeUsername)));
        settingsUsernameDisplayField.setText(activeUsername);
        settingsUserNameLabel.setText(fullName == null || fullName.isBlank() ? activeUsername : fullName);
        settingsAvatarLabel.setText(userInitials(fullName, activeUsername));
        settingsUniversityCombo.setValue(state.targetUniversityOfUser(activeUsername));
        Map<String, TargetRequirement> depMap = targetMathNetMap.getOrDefault(settingsUniversityCombo.getValue(), Map.of());
        settingsDepartmentCombo.setItems(FXCollections.observableArrayList(depMap.keySet()));
        settingsDepartmentCombo.setValue(state.targetDepartmentOfUser(activeUsername));
    }

    private String userInitials(String fullName, String username) {
        if (fullName != null && !fullName.isBlank()) {
            String[] parts = fullName.trim().split("\\s+");
            if (parts.length >= 2) {
                return (parts[0].substring(0, 1) + parts[parts.length - 1].substring(0, 1)).toUpperCase();
            }
            return parts[0].substring(0, Math.min(2, parts[0].length())).toUpperCase();
        }
        if (username != null && username.length() >= 2) {
            return username.substring(0, 2).toUpperCase();
        }
        return "AA";
    }

    private void initializeSettingsNavigation() {
        ToggleGroup settingsNavGroup = new ToggleGroup();
        settingsNavProfileButton.setToggleGroup(settingsNavGroup);
        settingsNavAppearanceButton.setToggleGroup(settingsNavGroup);
        settingsNavProfileButton.setSelected(true);
        showSettingsProfilePane();
    }

    @FXML
    private void onSettingsShowProfile() {
        showSettingsProfilePane();
    }

    @FXML
    private void onSettingsShowAppearance() {
        showSettingsAppearancePane();
    }

    private void showSettingsProfilePane() {
        settingsNavProfileButton.setSelected(true);
        settingsProfilePane.setVisible(true);
        settingsProfilePane.setManaged(true);
        settingsAppearancePane.setVisible(false);
        settingsAppearancePane.setManaged(false);
    }

    private void showSettingsAppearancePane() {
        settingsNavAppearanceButton.setSelected(true);
        settingsProfilePane.setVisible(false);
        settingsProfilePane.setManaged(false);
        settingsAppearancePane.setVisible(true);
        settingsAppearancePane.setManaged(true);
    }

    private void refreshLessonsByExamType(ExamType examType) {
        List<String> lessons = examType == ExamType.AYT
                ? List.of("Matematik", "Fizik", "Kimya", "Biyoloji", "Edebiyat", "Tarih", "Coğrafya")
                : List.of("Türkçe", "Matematik", "Sosyal", "Fen");
        examLessonsContainer.getChildren().clear();
        lessonCorrectFields.clear();
        lessonWrongFields.clear();
        for (String lesson : lessons) {
            int maxForLesson = ExamInputLimits.maxQuestions(examType, lesson);
            Label lessonLabel = new Label(lesson);
            lessonLabel.getStyleClass().add("lesson-row-label");
            lessonLabel.setMinWidth(140);
            TextField correct = new TextField();
            correct.setPromptText("Doğru (0-" + maxForLesson + ")");
            correct.setTooltip(new Tooltip("En fazla " + maxForLesson + " soru (doğru+yanlış toplamı limit)."));
            correct.getStyleClass().add("lesson-row-input");
            TextField wrong = new TextField();
            wrong.setPromptText("Yanlış (0-" + maxForLesson + ")");
            wrong.setTooltip(new Tooltip("En fazla " + maxForLesson + " soru (doğru+yanlış toplamı limit)."));
            wrong.getStyleClass().add("lesson-row-input");
            HBox row = new HBox(10, lessonLabel, correct, wrong);
            row.setAlignment(Pos.CENTER_LEFT);
            row.getStyleClass().add("lesson-row");
            HBox.setHgrow(correct, Priority.ALWAYS);
            HBox.setHgrow(wrong, Priority.ALWAYS);
            examLessonsContainer.getChildren().add(row);
            lessonCorrectFields.put(lesson, correct);
            lessonWrongFields.put(lesson, wrong);
        }
    }

    private void refreshCharts() {
        tytScoreLineChart.getData().clear();
        aytScoreLineChart.getData().clear();
        XYChart.Series<String, Number> tytScoreSeries = new XYChart.Series<>();
        XYChart.Series<String, Number> aytScoreSeries = new XYChart.Series<>();
        for (MockExam exam : state.exams()) {
            if (exam.getExamType() == ExamType.TYT) {
                tytScoreSeries.getData().add(new XYChart.Data<>(exam.getDate().toString(), exam.getEstimatedScore()));
            } else if (exam.getExamType() == ExamType.AYT) {
                aytScoreSeries.getData().add(new XYChart.Data<>(exam.getDate().toString(), exam.getEstimatedScore()));
            }
        }
        tytScoreLineChart.getData().add(tytScoreSeries);
        aytScoreLineChart.getData().add(aytScoreSeries);

        tytLessonBarChart.getData().clear();
        aytLessonBarChart.getData().clear();
        XYChart.Series<String, Number> tytLessonSeries = new XYChart.Series<>();
        XYChart.Series<String, Number> aytLessonSeries = new XYChart.Series<>();
        tytLessonSeries.setName("TYT Ortalama Net");
        aytLessonSeries.setName("AYT Ortalama Net");

        Map<String, List<Double>> tytGrouped = new LinkedHashMap<>();
        Map<String, List<Double>> aytGrouped = new LinkedHashMap<>();
        for (MockExam exam : state.exams()) {
            for (LessonResult lesson : exam.getLessonResults()) {
                double net = lesson.getCorrect() - lesson.getWrong() / 4.0;
                if (exam.getExamType() == ExamType.TYT) {
                    tytGrouped.computeIfAbsent(normalizeLessonDisplay(lesson.getLessonName()), k -> new ArrayList<>()).add(net);
                } else if (exam.getExamType() == ExamType.AYT) {
                    aytGrouped.computeIfAbsent(normalizeLessonDisplay(lesson.getLessonName()), k -> new ArrayList<>()).add(net);
                }
            }
        }

        tytGrouped.forEach((lesson, values) -> {
            double avg = values.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
            tytLessonSeries.getData().add(new XYChart.Data<>(lesson, avg));
        });
        aytGrouped.forEach((lesson, values) -> {
            double avg = values.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
            aytLessonSeries.getData().add(new XYChart.Data<>(lesson, avg));
        });

        tytLessonBarChart.getData().add(tytLessonSeries);
        aytLessonBarChart.getData().add(aytLessonSeries);
    }

    private void refreshWeekly() {
        Comparator<WeeklyQuestionEntry> byWeekDesc = (a, b) -> {
            LocalDate wa = a.getWeekStart();
            LocalDate wb = b.getWeekStart();
            if (wa == null && wb == null) return 0;
            if (wa == null) return 1;
            if (wb == null) return -1;
            return wb.compareTo(wa);
        };
        List<WeeklyQuestionEntry> entries = state.weeklyQuestions().stream().sorted(byWeekDesc).toList();
        weeklyQuestionCardsContainer.getChildren().clear();
        if (entries.isEmpty()) {
            Label empty = new Label(isEnglishUi() ? "No weekly entries yet." : "Henüz haftalık kayıt yok.");
            empty.getStyleClass().add("metric-note");
            empty.setWrapText(true);
            weeklyQuestionCardsContainer.getChildren().add(empty);
            return;
        }
        for (WeeklyQuestionEntry w : entries) {
            weeklyQuestionCardsContainer.getChildren().add(buildWeeklyQuestionCard(w));
        }
    }

    private VBox buildWeeklyQuestionCard(WeeklyQuestionEntry w) {
        String weekPart = w.getWeekStart() != null ? w.getWeekStart().toString()
                : (isEnglishUi() ? "(no week)" : "(hafta yok)");
        Label weekLabel = new Label(weekPart);
        weekLabel.getStyleClass().add("weekly-card-week");

        String lesson = w.getLesson() != null ? normalizeLessonDisplay(w.getLesson()) : "—";
        String topic = w.getTopic() != null ? normalizeTopicDisplay(w.getTopic()) : "—";
        Label lessonLabel = new Label(isEnglishUi() ? "Lesson: " + lesson : "Ders: " + lesson);
        lessonLabel.getStyleClass().add("weekly-card-lesson");

        Label topicLabel = new Label(isEnglishUi() ? "Topic: " + topic : "Konu: " + topic);
        topicLabel.getStyleClass().add("weekly-card-topic");
        topicLabel.setWrapText(true);

        Label qLabel = new Label(isEnglishUi()
                ? ("Questions: " + w.getQuestionCount())
                : ("Soru: " + w.getQuestionCount()));
        qLabel.getStyleClass().add("weekly-card-qcount");

        Label dyLabel = new Label(isEnglishUi()
                ? ("Correct: " + w.getCorrectCount() + "  |  Wrong: " + w.getWrongCount())
                : ("Doğru: " + w.getCorrectCount() + "  |  Yanlış: " + w.getWrongCount()));
        dyLabel.getStyleClass().add("weekly-card-dy");

        HBox metricsRow = new HBox(16, qLabel, dyLabel);
        metricsRow.setAlignment(Pos.CENTER_LEFT);

        VBox card = new VBox(8, weekLabel, lessonLabel, topicLabel, metricsRow);
        card.getStyleClass().addAll("feed-card", "weekly-tracking-card");
        card.setMaxWidth(Double.MAX_VALUE);
        return card;
    }

    private void refreshEvents() {
        eventCardsContainer.getChildren().clear();
        for (ExamCalendarEvent event : state.turkeyGeneralExamCalendar()) {
            eventCardsContainer.getChildren().add(buildInfoCard(event.getTitle(), event.getPublisher(), "Tarih: " + event.getDate(), event.getLink()));
        }
    }

    private void refreshNews() {
        newsCardsContainer.getChildren().clear();
        for (NewsItem item : state.news()) {
            newsCardsContainer.getChildren().add(buildInfoCard(item.getTitle(), item.getSource(), "Tarih: " + item.getDate(), item.getLink()));
        }
    }

    private void refreshDashboardFeeds() {
        dashboardNewsContainer.getChildren().clear();
        dashboardExamsContainer.getChildren().clear();

        List<NewsItem> topNews = state.news().stream().limit(5).toList();
        for (NewsItem item : topNews) {
            dashboardNewsContainer.getChildren().add(buildCompactDashboardCard(
                    item.getTitle(),
                    item.getDate() + " | " + item.getSource(),
                    item.getLink()
            ));
        }

        List<ExamCalendarEvent> upcomingExams = state.turkeyGeneralExamCalendar().stream()
                .sorted(Comparator.comparing(ExamCalendarEvent::getDate))
                .limit(3)
                .toList();
        for (ExamCalendarEvent exam : upcomingExams) {
            dashboardExamsContainer.getChildren().add(buildCompactDashboardCard(
                    exam.getTitle(),
                    exam.getDate() + " | " + exam.getPublisher(),
                    exam.getLink()
            ));
        }
    }

    private void refreshTargetProgress() {
        if (activeUsername == null || activeUsername.isBlank()) {
            targetInfoLabel.setText("Hedef seçilmedi.");
            targetRemainingLabel.setText("");
            targetStatusLabel.setText("");
            goalInfoLabel.setText("Hedef seçilmedi.");
            goalRemainingLabel.setText("");
            goalStatusLabel.setText("");
            populateGoalTargetEmptyState("Hedef bilgilerini görmek için giriş yapın.");
            clearGoalKpiPlaceholders();
            return;
        }
        String uni = state.targetUniversityOfUser(activeUsername);
        String dept = state.targetDepartmentOfUser(activeUsername);
        double requiredMathNet = state.requiredMathNetOfUser(activeUsername);
        double requiredTytNet = state.requiredTytNetOfUser(activeUsername);
        double requiredAytNet = state.requiredAytNetOfUser(activeUsername);
        double requiredAverageScore = state.requiredAverageScoreOfUser(activeUsername);
        long requiredRanking = state.requiredRankingOfUser(activeUsername);
        if (uni == null || uni.isBlank() || dept == null || dept.isBlank()) {
            targetInfoLabel.setText("Hedef seçilmedi.");
            targetRemainingLabel.setText("");
            targetStatusLabel.setText("Kayıt olurken hedef seçimi isteğe bağlıdır.");
            goalInfoLabel.setText("Hedef seçilmedi.");
            goalRemainingLabel.setText("");
            goalStatusLabel.setText("Kayıt olurken hedef seçimi isteğe bağlıdır.");
            populateGoalTargetEmptyState("Hedef seçersen kalan netler ve ders ortalamaları burada kartlar halinde görünür.");
            clearGoalKpiPlaceholders();
            goalLessonBreakdownContainer.getChildren().clear();
            return;
        }
        double currentMathNet = currentMathNetAverage();
        double currentTytNet = currentTotalNetAverage(ExamType.TYT);
        double currentAytNet = currentTotalNetAverage(ExamType.AYT);
        int userObp = state.obpOfUser(activeUsername);
        Optional<MockExam> lastTyt = PlacementProxyService.newestExamOfType(state.exams(), ExamType.TYT);
        Optional<MockExam> lastAyt = PlacementProxyService.newestExamOfType(state.exams(), ExamType.AYT);
        double tytEst = lastTyt.map(MockExam::getEstimatedScore).orElse(0.0);
        double aytEst = lastAyt.map(MockExam::getEstimatedScore).orElse(0.0);
        boolean hasExamForProxy = lastTyt.isPresent() || lastAyt.isPresent();
        double combinedProxy = PlacementProxyService.combinedPlacementProxy(tytEst, aytEst, userObp);
        long estimatedRank = -1;
        if (hasExamForProxy && requiredRanking > 0 && requiredAverageScore > 0) {
            estimatedRank = RankingEstimator.estimateRankFromReference(combinedProxy, requiredAverageScore, requiredRanking);
        }
        String tytExplainTr = lastTyt.map(e -> {
            String d = e.getDate() != null ? e.getDate().toString() : "?";
            return String.format("Son TYT (%s): %.1f", d, e.getEstimatedScore());
        }).orElse("Son TYT: yok");
        String aytExplainTr = lastAyt.map(e -> {
            String d = e.getDate() != null ? e.getDate().toString() : "?";
            return String.format("Son AYT (%s): %.1f", d, e.getEstimatedScore());
        }).orElse("Son AYT: yok");
        String tytExplainEn = lastTyt.map(e -> {
            String d = e.getDate() != null ? e.getDate().toString() : "?";
            return String.format("Latest TYT (%s): %.1f", d, e.getEstimatedScore());
        }).orElse("Latest TYT: none");
        String aytExplainEn = lastAyt.map(e -> {
            String d = e.getDate() != null ? e.getDate().toString() : "?";
            return String.format("Latest AYT (%s): %.1f", d, e.getEstimatedScore());
        }).orElse("Latest AYT: none");
        double remainingTyt = requiredTytNet - currentTytNet;
        double remainingAyt = requiredAytNet - currentAytNet;
        targetInfoLabel.setText(uni + " - " + dept);
        targetRemainingLabel.setText(String.format("Kalan Netler | TYT: %.1f, AYT: %.1f", Math.max(remainingTyt, 0), Math.max(remainingAyt, 0)));
        String combinedTxt = hasExamForProxy ? String.format("%.1f", combinedProxy) : "—";
        String estRankTxt = estimatedRank > 0
                ? formatRankNumber(estimatedRank)
                : (!hasExamForProxy ? (isEnglishUi() ? "Add exams" : "Deneme gerekli") : "—");
        String refRankTxt = requiredRanking > 0 ? formatRankNumber(requiredRanking) : "—";
        String fullTargetStatus;
        if (isEnglishUi()) {
            targetStatusLabel.setText(String.format("Proxy: %s | Est. rank: %s | OBP: %d", combinedTxt, estRankTxt, userObp));
            fullTargetStatus = String.format("Combined proxy (last TYT+AYT+OBP): %s | Est. rank ~%s | Ref. rank: %s | OBP: %d\n%s | %s\nMath %.1f/%.1f | TYT net avg %.1f/%.1f | AYT net avg %.1f/%.1f | Ref. score %.1f",
                    combinedTxt, estRankTxt, refRankTxt, userObp,
                    tytExplainEn, aytExplainEn,
                    currentMathNet, requiredMathNet, currentTytNet, requiredTytNet, currentAytNet, requiredAytNet, requiredAverageScore);
        } else {
            targetStatusLabel.setText(String.format("Gösterge: %s | Tahmini sıra: %s | OBP: %d", combinedTxt, estRankTxt, userObp));
            fullTargetStatus = String.format("Gösterge (son TYT+AYT+OBP): %s | Tahmini sıra ~%s | Ref. sıra: %s | OBP: %d\n%s | %s\nMatematik %.1f/%.1f | TYT ort. net %.1f/%.1f | AYT ort. net %.1f/%.1f | Hedef (ref.) puan %.1f",
                    combinedTxt, estRankTxt, refRankTxt, userObp,
                    tytExplainTr, aytExplainTr,
                    currentMathNet, requiredMathNet, currentTytNet, requiredTytNet, currentAytNet, requiredAytNet, requiredAverageScore);
        }
        populateGoalTargetStatsCards(
                currentTytNet, requiredTytNet, remainingTyt,
                currentAytNet, requiredAytNet, remainingAyt,
                currentMathNet, requiredMathNet,
                hasExamForProxy, combinedProxy, requiredAverageScore, estimatedRank, requiredRanking, userObp
        );

        goalInfoLabel.setText(targetInfoLabel.getText());
        if (!hasExamForProxy) {
            goalLastExamScoreLabel.setText("—");
            goalLastExamRankLabel.setText(isEnglishUi() ? "Add TYT or AYT" : "TYT veya AYT girin");
            goalPlacementBreakdownLabel.setText(isEnglishUi()
                    ? String.format("OBP: %d (1-100). Enter at least one TYT or AYT mock to compute proxy rank.", userObp)
                    : String.format("OBP: %d (1-100). Sıralama için en az bir TYT veya AYT denemesi gerekir.", userObp));
        } else {
            goalLastExamScoreLabel.setText(String.format("%.1f", combinedProxy));
            goalLastExamRankLabel.setText(estimatedRank > 0 ? ("~" + formatRankNumber(estimatedRank)) : "—");
            goalPlacementBreakdownLabel.setText(isEnglishUi()
                    ? String.format("%s | %s | OBP: %d", tytExplainEn, aytExplainEn, userObp)
                    : String.format("%s | %s | OBP: %d", tytExplainTr, aytExplainTr, userObp));
        }
        goalTargetAverageScoreLabel.setText(requiredAverageScore > 0 ? String.format("%.1f", requiredAverageScore) : "—");
        goalTargetRankingLabel.setText(requiredRanking > 0 ? formatRankNumber(requiredRanking) : "—");
        goalRemainingLabel.setText(targetRemainingLabel.getText());
        String disclaimer = isEnglishUi()
                ? "Proxy blends latest TYT & AYT mock scores with OBP (1-100). Rankings are illustrative vs. Atlas-style reference, not official ÖSYM."
                : "Gösterge son TYT/AYT deneme tahminleri ile OBP birleşimidir. Sıralama Atlas referansı ile yaklaşıktır; resmi yerleştirme değildir.";
        goalStatusLabel.setText(fullTargetStatus + "\n" + disclaimer);
        refreshGoalLessonBreakdown();
    }

    private void populateGoalTargetEmptyState(String message) {
        goalTargetStatsContainer.getChildren().setAll(buildTargetMetricCard(
                "Kalan Netler",
                "—",
                message,
                "target-card-neutral"
        ));
    }

    private void populateGoalTargetStatsCards(
            double currentTytNet, double requiredTytNet, double remainingTyt,
            double currentAytNet, double requiredAytNet, double remainingAyt,
            double currentMathNet, double requiredMathNet,
            boolean hasExamForProxy, double combinedProxy, double requiredAverageScore, long estimatedRank, long requiredRanking, int userObp
    ) {
        boolean en = isEnglishUi();
        String estimatedRankText = estimatedRank > 0 ? "~" + formatRankNumber(estimatedRank) : (hasExamForProxy ? "—" : (en ? "Add exams" : "Deneme gerekli"));
        String referenceRankText = requiredRanking > 0 ? formatRankNumber(requiredRanking) : "—";

        VBox header = new VBox(3);
        Label title = new Label(en ? "Remaining Nets" : "Kalan Netler");
        title.getStyleClass().add("target-section-title");
        Label subtitle = new Label(en
                ? "Your current position against the selected goal, grouped for quick scanning."
                : "Seçili hedefe göre güncel durumun; özet, TYT, AYT ve ders ortalamaları.");
        subtitle.getStyleClass().add("target-section-subtitle");
        subtitle.setWrapText(true);
        header.getChildren().addAll(title, subtitle);

        FlowPane summaryGrid = targetMiniGrid();
        summaryGrid.getChildren().addAll(
                buildTargetMetricCard(en ? "General Status" : "Genel Durum",
                        hasExamForProxy ? String.format("%.1f", combinedProxy) : "—",
                        en ? "Proxy score" : "Gösterge puan",
                        progressStyle(combinedProxy, requiredAverageScore > 0 ? requiredAverageScore : requiredAverageFallback(requiredTytNet, requiredAytNet))),
                buildTargetMetricCard(en ? "Estimated Rank" : "Tahmini Sıra",
                        estimatedRankText,
                        (en ? "Reference: " : "Referans: ") + referenceRankText,
                        estimatedRank > 0 && requiredRanking > 0 && estimatedRank <= requiredRanking ? "target-card-good" : "target-card-neutral"),
                buildTargetMetricCard("OBP",
                        String.valueOf(userObp),
                        en ? "Profile score (1-100)" : "Profil puanı (1-100)",
                        userObp >= 80 ? "target-card-good" : userObp >= 60 ? "target-card-medium" : "target-card-danger")
        );

        Node tytSection = buildTargetSection(en ? "TYT Nets" : "TYT Netleri",
                en ? "Current average, goal and remaining gap." : "Güncel ortalama, hedef ve kalan fark.",
                buildProgressMetricCard("TYT", currentTytNet, requiredTytNet, remainingTyt));

        Node aytSection = buildTargetSection(en ? "AYT Nets" : "AYT Netleri",
                en ? "Current average, goal and remaining gap." : "Güncel ortalama, hedef ve kalan fark.",
                buildProgressMetricCard("AYT", currentAytNet, requiredAytNet, remainingAyt));

        FlowPane lessonGrid = targetMiniGrid();
        Map<String, Double> lessonAverages = currentLessonNetAverages();
        if (lessonAverages.isEmpty()) {
            lessonGrid.getChildren().add(buildTargetMetricCard(
                    en ? "Subjects" : "Dersler",
                    "—",
                    en ? "Add mock exams to see subject averages." : "Ders ortalamaları için deneme ekleyin.",
                    "target-card-neutral"
            ));
        } else {
            lessonAverages.forEach((lesson, average) -> lessonGrid.getChildren().add(buildTargetMetricCard(
                    lesson,
                    String.format("%.1f", average),
                    en ? "Average net" : "Ortalama net",
                    average >= 25 ? "target-card-good" : average >= 12 ? "target-card-medium" : "target-card-danger"
            )));
        }

        Node lessonsSection = buildTargetSection(en ? "Subject Averages" : "Ders Bazlı Ortalamalar",
                en ? "Average net values by lesson." : "Derslere göre ortalama net değerleri.",
                lessonGrid);

        goalTargetStatsContainer.getChildren().setAll(header, summaryGrid, tytSection, aytSection, lessonsSection);
    }

    private double requiredAverageFallback(double requiredTytNet, double requiredAytNet) {
        return (requiredTytNet + requiredAytNet) * 2.4;
    }

    private VBox buildTargetSection(String titleText, String subtitleText, Node content) {
        VBox section = new VBox(8);
        section.getStyleClass().add("target-stats-section");

        Label title = new Label(titleText);
        title.getStyleClass().add("target-group-title");
        Label subtitle = new Label(subtitleText);
        subtitle.getStyleClass().add("target-group-subtitle");
        subtitle.setWrapText(true);

        section.getChildren().addAll(title, subtitle, content);
        return section;
    }

    private FlowPane targetMiniGrid() {
        FlowPane grid = new FlowPane(10, 10);
        grid.getStyleClass().add("target-mini-grid");
        grid.setPrefWrapLength(360);
        grid.setMaxWidth(Double.MAX_VALUE);
        return grid;
    }

    private VBox buildTargetMetricCard(String labelText, String valueText, String noteText, String statusClass) {
        VBox card = new VBox(4);
        card.getStyleClass().addAll("target-mini-card", statusClass);
        card.setMinWidth(112);
        card.setPrefWidth(132);

        Label label = new Label(labelText);
        label.getStyleClass().add("target-mini-label");
        label.setWrapText(true);
        Label value = new Label(valueText);
        value.getStyleClass().add("target-mini-value");
        value.setWrapText(true);
        Label note = new Label(noteText);
        note.getStyleClass().add("target-mini-note");
        note.setWrapText(true);

        card.getChildren().addAll(label, value, note);
        return card;
    }

    private VBox buildProgressMetricCard(String label, double current, double required, double remaining) {
        double progress = required <= 0 ? 0 : Math.max(0, Math.min(1, current / required));
        String statusClass = progress >= 0.85 ? "target-card-good" : progress >= 0.60 ? "target-card-medium" : "target-card-danger";

        VBox card = buildTargetMetricCard(
                label,
                String.format("%.1f / %.1f", current, required),
                (isEnglishUi() ? "Remaining: " : "Kalan: ") + String.format("%.1f", Math.max(remaining, 0)),
                statusClass
        );

        ProgressBar bar = new ProgressBar(progress);
        bar.setMinWidth(92);
        bar.setPrefWidth(118);
        bar.setMinHeight(8);
        bar.setPrefHeight(8);
        bar.setMaxWidth(Double.MAX_VALUE);
        bar.getStyleClass().addAll("target-progress-bar", progress >= 0.85 ? "target-progress-good" : progress >= 0.60 ? "target-progress-medium" : "target-progress-danger");
        card.getChildren().add(bar);
        return card;
    }

    private String progressStyle(double value, double target) {
        if (target <= 0) {
            return "target-card-neutral";
        }
        double ratio = value / target;
        if (ratio >= 0.85) return "target-card-good";
        if (ratio >= 0.60) return "target-card-medium";
        return "target-card-danger";
    }

    private Map<String, Double> currentLessonNetAverages() {
        Map<String, List<Double>> grouped = new TreeMap<>();
        for (MockExam exam : state.exams()) {
            for (LessonResult result : exam.getLessonResults()) {
                String rawLesson = result.getLessonName() == null || result.getLessonName().isBlank()
                        ? "Ders"
                        : normalizeLessonDisplay(result.getLessonName());
                String lesson = "Matematik".equalsIgnoreCase(rawLesson) && exam.getExamType() != null
                        ? exam.getExamType().name() + " Matematik"
                        : rawLesson;
                double net = result.getCorrect() - result.getWrong() / 4.0;
                grouped.computeIfAbsent(lesson, ignored -> new ArrayList<>()).add(net);
            }
        }

        Map<String, Double> averages = new LinkedHashMap<>();
        grouped.forEach((lesson, values) -> averages.put(lesson, values.stream().mapToDouble(Double::doubleValue).average().orElse(0.0)));
        return averages;
    }

    private void clearGoalKpiPlaceholders() {
        goalLastExamScoreLabel.setText("-");
        goalLastExamRankLabel.setText("-");
        goalTargetAverageScoreLabel.setText("-");
        goalTargetRankingLabel.setText("-");
        goalPlacementBreakdownLabel.setText("");
    }

    /** OBP kullanıcı girişi 1-100 tam sayı. */
    private Optional<Integer> parseObp1To100(String raw) {
        if (raw == null || raw.isBlank()) {
            return Optional.empty();
        }
        try {
            int v = Integer.parseInt(raw.trim());
            if (v < 1 || v > 100) {
                return Optional.empty();
            }
            return Optional.of(v);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private String formatRankNumber(long rank) {
        Locale loc = isEnglishUi() ? Locale.US : Locale.forLanguageTag("tr-TR");
        return NumberFormat.getIntegerInstance(loc).format(rank);
    }

    private double currentMathNetAverage() {
        return state.exams().stream()
                .flatMap(e -> e.getLessonResults().stream())
                .filter(r -> "Matematik".equalsIgnoreCase(r.getLessonName()))
                .mapToDouble(r -> r.getCorrect() - r.getWrong() / 4.0)
                .average()
                .orElse(0.0);
    }

    private double currentTotalNetAverage(ExamType type) {
        return state.exams().stream()
                .filter(e -> e.getExamType() == type)
                .mapToDouble(e -> e.getLessonResults().stream()
                        .mapToDouble(r -> r.getCorrect() - r.getWrong() / 4.0)
                        .sum())
                .average()
                .orElse(0.0);
    }

    private String normalizeLessonDisplay(String lessonName) {
        if (lessonName == null) {
            return "";
        }
        return switch (lessonName.trim()) {
            case "Turkce" -> "Türkçe";
            case "Cografya" -> "Coğrafya";
            default -> lessonName.trim();
        };
    }

    private String normalizeTopicDisplay(String topic) {
        if (topic == null) {
            return "";
        }
        return switch (topic.trim()) {
            case "Turev" -> "Türev";
            case "Sozcukte Anlam" -> "Sözcükte Anlam";
            case "Cumlede Anlam" -> "Cümlede Anlam";
            case "Cozeltiler" -> "Çözeltiler";
            case "Hucre" -> "Hücre";
            case "Ilk Cag" -> "İlk Çağ";
            case "Osmanli" -> "Osmanlı";
            case "Kurtulus Savasi" -> "Kurtuluş Savaşı";
            case "Inkilap Tarihi" -> "İnkılap Tarihi";
            case "Iklim" -> "İklim";
            case "Nufus" -> "Nüfus";
            case "Turkiye Cografyasi" -> "Türkiye Coğrafyası";
            default -> topic.trim();
        };
    }

    private VBox buildInfoCard(String title, String source, String meta, String link) {
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("feed-card-title");
        titleLabel.setWrapText(true);
        Label sourceLabel = new Label("Kaynak: " + source);
        sourceLabel.getStyleClass().add("feed-card-source");
        Label metaLabel = new Label(meta);
        metaLabel.getStyleClass().add("feed-card-meta");
        Hyperlink openLink = new Hyperlink("Linke git");
        openLink.getStyleClass().add("feed-card-link");
        openLink.setVisible(link != null && !link.isBlank());
        openLink.setManaged(link != null && !link.isBlank());
        openLink.setOnAction(event -> openExternalLink(link));
        VBox card = new VBox(6, titleLabel, sourceLabel, metaLabel, openLink);
        card.getStyleClass().add("feed-card");
        return card;
    }

    private VBox buildCompactDashboardCard(String title, String meta, String link) {
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("dashboard-item-title");
        titleLabel.setWrapText(true);

        Label metaLabel = new Label(meta);
        metaLabel.getStyleClass().add("dashboard-item-meta");

        Hyperlink linkLabel = new Hyperlink("Detaya git");
        linkLabel.getStyleClass().add("feed-card-link");
        linkLabel.setVisible(link != null && !link.isBlank());
        linkLabel.setManaged(link != null && !link.isBlank());
        linkLabel.setOnAction(e -> openExternalLink(link));

        VBox box = new VBox(5, titleLabel, metaLabel, linkLabel);
        box.getStyleClass().add("dashboard-item-card");
        return box;
    }

    private void openExternalLink(String link) {
        try {
            if (link != null && !link.isBlank() && Desktop.isDesktopSupported()) Desktop.getDesktop().browse(new URI(link));
        } catch (Exception ignored) { }
    }

    private void initializePreferences() {
        themeCombo.setItems(FXCollections.observableArrayList("dark", "light"));
        languageCombo.setItems(FXCollections.observableArrayList("tr", "en"));
        themeCombo.setValue(state.themeMode() == null || state.themeMode().isBlank() ? "dark" : state.themeMode());
        languageCombo.setValue(state.language() == null || state.language().isBlank() ? "tr" : state.language());
        applyTheme(themeCombo.getValue());
        applyLanguage(languageCombo.getValue());
    }

    private void applyTheme(String theme) {
        rootPane.getStyleClass().remove("light-theme");
        if ("light".equalsIgnoreCase(theme)) rootPane.getStyleClass().add("light-theme");
    }

    private void applyLanguage(String language) {
        boolean en = "en".equalsIgnoreCase(language);
        appTitleLabel.setText(en ? "Alperen Academy" : "Alperen Akademi");
        appSubtitleLabel.setText(en ? "YKS Mock Exam Performance Tracking System" : "YKS Deneme Performans ve Gelişim Takip Sistemi");
        dashboardTab.setText("Dashboard");
        examTab.setText(en ? "Exam Entry" : "Deneme Giriş");
        examListTab.setText(en ? "Saved Exams" : "Deneme Listesi");
        examListIntroLabel.setText(en ? "Recorded exams (newest first)" : "Kayıtlı denemeler (en yeni üstte)");
        tytExamListTitleLabel.setText(en ? "TYT exams" : "TYT Denemeleri");
        aytExamListTitleLabel.setText(en ? "AYT exams" : "AYT Denemeleri");
        examDateHintLabel.setText(en ? "Selecting the exam date is required." : "Deneme tarihi seçmek zorunludur.");
        examDatePicker.setPromptText(en ? "Exam date (required)" : "Deneme tarihi (zorunlu)");
        analysisTab.setText(en ? "Charts & Analysis" : "Grafik ve Analiz");
        weeklyTab.setText(en ? "Weekly Tracking" : "Haftalık Soru Takibi");
        goalTab.setText(en ? "Goals" : "Hedef");
        calendarTab.setText(en ? "Calendar" : "Takvim");
        newsTab.setText(en ? "News" : "Haberler");
        settingsTab.setText(en ? "Settings" : "Ayarlar");
        updateSidebarLabels(en);
        calendarTitleLabel.setText(en ? "Nationwide Mock Exam Calendar" : "Türkiye Geneli Deneme Takvimi");
        calendarInfoLabel.setText(en ? "Calendar data is fetched from the internet." : "Takvim verileri internetten otomatik çekilir.");
        refreshCalendarButton.setText(en ? "Refresh Calendar" : "Takvimi Yenile");
        newsTitleLabel.setText(en ? "Latest YKS News Flow" : "Güncel Haber Akışı");
        settingsNavProfileButton.setText(en ? "Profile" : "Profil");
        settingsNavAppearanceButton.setText(en ? "Appearance" : "Görünüm");
        settingsContentTitleLabel.setText(en ? "Profile Settings" : "Profil Ayarları");
        settingsAppearanceTitleLabel.setText(en ? "Appearance Settings" : "Görünüm Ayarları");
        settingsGroupProfileLabel.setText(en ? "Profile details" : "Profil bilgileri");
        settingsGroupContactLabel.setText(en ? "Contact & target" : "İletişim ve hedef");
        settingsAppearanceGroupLabel.setText(en ? "Preferences" : "Tercihler");
        settingsNameLabel.setText(en ? "Name" : "Ad Soyad");
        settingsUsernameLabel.setText(en ? "Username" : "Kullanıcı adı");
        settingsEmailLabel.setText(en ? "Email" : "E-posta");
        settingsUniversityLabel.setText(en ? "Target university" : "Hedef üniversite");
        settingsUniversityCombo.setPromptText(en ? "Select university" : "Üniversite seç");
        settingsDepartmentLabel.setText(en ? "Target department" : "Hedef bölüm");
        settingsDepartmentCombo.setPromptText(en ? "Select department" : "Bölüm seç");
        settingsObpLabel.setText("OBP");
        settingsObpField.setPromptText(en ? "1-100" : "1-100");
        updateProfileButton.setText(en ? "Save Changes" : "Değişiklikleri Kaydet");
        themeLabel.setText(en ? "Theme" : "Tema");
        themeCombo.setPromptText(en ? "Dark / Light" : "Gece / Gündüz");
        languageLabel.setText(en ? "Language" : "Dil");
        languageCombo.setPromptText(en ? "Turkish / English" : "Türkçe / English");
        saveSettingsButton.setText(en ? "Save Changes" : "Değişiklikleri Kaydet");
        refreshSettingsProfile();
        if (yksCountdownRingsPanel != null) {
            yksCountdownRingsPanel.setEnglish(en);
        }
        authTitleLabel.setText("Alperen Akademi");
        authSubtitleLabel.setText(en
                ? "YKS Mock Exam Performance and Progress Tracking"
                : "YKS Deneme Performans ve Gelişim Takip Sistemi");
        loginFormHeadingLabel.setText(en ? "Sign In" : "Giriş Yap");
        loginUsernameField.setPromptText(en ? "Username" : "Kullanıcı adı");
        loginPasswordField.setPromptText(en ? "Password" : "Şifre");
        loginButton.setText(en ? "Sign In" : "Giriş Yap");
        showRegisterButton.setText(en ? "Don't have an account? Sign up" : "Hesabınız yok mu? Kayıt olun");
        registerFormHeadingLabel.setText(en ? "Sign Up" : "Kayıt Ol");
        registerFullNameField.setPromptText(en ? "Full name" : "Ad Soyad");
        registerEmailField.setPromptText(en ? "Email" : "E-posta");
        registerUsernameField.setPromptText(en ? "Username" : "Kullanıcı adı");
        registerPasswordField.setPromptText(en ? "Password" : "Şifre");
        registerObpField.setPromptText(en ? "OBP score (1-100)" : "OBP puanı (1-100)");
        registerObpHintLabel.setText(en
                ? "OBP required: integer 1-100. Used with your latest TYT/AYT mocks."
                : "OBP zorunludur (1-100 tam sayı); son TYT ve AYT denemeleriniz ile birleştirilir.");
        registerSectionTargetLabel.setText(en ? "Target (optional)" : "Hedef (isteğe bağlı)");
        registerUniversityCombo.setPromptText(en ? "Select university" : "Üniversite seç");
        registerDepartmentCombo.setPromptText(en ? "Select department" : "Bölüm seç");
        registerButton.setText(en ? "Sign Up" : "Kayıt Ol");
        showLoginButton.setText(en ? "Already have an account? Sign in" : "Zaten hesabınız var mı? Giriş yapın");
        settingsObpField.setPromptText(en ? "OBP (1-100)" : "OBP (1-100)");
        goalAnalysisHeadingLabel.setText(en ? "Goal analysis (YÖK Atlas reference)" : "Hedef analizi (YÖK Atlas referanslı)");
        goalLastExamScoreHeading.setText(en ? "Proxy score (last TYT+AYT+OBP)" : "Gösterge puan (son TYT+AYT+OBP)");
        goalLastExamRankHeading.setText(en ? "Estimated rank (vs. Atlas ref.)" : "Tahmini sıralama (Atlas ref.)");
        goalTargetScoreHeading.setText(en ? "Target score (YÖK Atlas)" : "Hedef puan (YÖK Atlas)");
        goalTargetRankHeading.setText(en ? "Reference placement rank (illustrative)" : "Referans yerleşme sırası (örnek Atlas)");
    }

    private void updateSidebarLabels(boolean en) {
        sidebarDashboardButton.setText(navText("⌂", "Dashboard"));
        sidebarExamButton.setText(navText("✎", en ? "Exam Entry" : "Deneme Giriş"));
        sidebarExamListButton.setText(navText("☷", en ? "Saved Exams" : "Deneme Listesi"));
        sidebarAnalysisButton.setText(navText("▣", en ? "Charts & Analysis" : "Grafik ve Analiz"));
        sidebarWeeklyButton.setText(navText("✓", en ? "Weekly Tracking" : "Haftalık Soru Takibi"));
        sidebarGoalButton.setText(navText("◎", en ? "Goals" : "Hedef"));
        sidebarCalendarButton.setText(navText("▦", en ? "Calendar" : "Takvim"));
        sidebarNewsButton.setText(navText("✦", en ? "News" : "Haberler"));
        sidebarSettingsButton.setText(navText("⚙", en ? "Settings" : "Ayarlar"));
        logoutButton.setText(navText("⏻", en ? "Sign Out" : "Çıkış Yap"));
    }

    private String navText(String icon, String label) {
        return sidebarCollapsed ? icon : icon + "  " + label;
    }

    private void initializeWeeklyTrackingSelections() {
        weeklyLessonCombo.setItems(FXCollections.observableArrayList(weeklyTopicsByLesson.keySet()));
        weeklyLessonCombo.valueProperty().addListener((obs, oldLesson, newLesson) -> {
            List<String> topics = weeklyTopicsByLesson.getOrDefault(newLesson, List.of());
            weeklyTopicCombo.setItems(FXCollections.observableArrayList(topics));
            if (!topics.isEmpty()) weeklyTopicCombo.setValue(topics.get(0));
        });
        if (!weeklyTopicsByLesson.isEmpty()) weeklyLessonCombo.setValue(weeklyTopicsByLesson.keySet().iterator().next());
    }

    private void initializeTargetSelection() {
        registerUniversityCombo.setItems(FXCollections.observableArrayList(targetMathNetMap.keySet()));
        registerUniversityCombo.valueProperty().addListener((obs, oldUni, newUni) -> {
            Map<String, TargetRequirement> departments = targetMathNetMap.getOrDefault(newUni, Map.of());
            registerDepartmentCombo.setItems(FXCollections.observableArrayList(departments.keySet()));
            registerDepartmentCombo.setValue(null);
        });

        settingsUniversityCombo.setItems(FXCollections.observableArrayList(targetMathNetMap.keySet()));
        settingsUniversityCombo.valueProperty().addListener((obs, oldUni, newUni) -> {
            Map<String, TargetRequirement> departments = targetMathNetMap.getOrDefault(newUni, Map.of());
            settingsDepartmentCombo.setItems(FXCollections.observableArrayList(departments.keySet()));
            settingsDepartmentCombo.setValue(null);
        });
    }

    private double selectedRequiredMathNet(String uni, String dept) {
        if (uni == null || dept == null) return 0.0;
        return targetMathNetMap.getOrDefault(uni, Map.of()).getOrDefault(dept, ZERO_TARGET_REQUIREMENT).requiredMathNet;
    }

    private double selectedRequiredTytNet(String uni, String dept) {
        if (uni == null || dept == null) return 0.0;
        return targetMathNetMap.getOrDefault(uni, Map.of()).getOrDefault(dept, ZERO_TARGET_REQUIREMENT).requiredTytNet;
    }

    private double selectedRequiredAytNet(String uni, String dept) {
        if (uni == null || dept == null) return 0.0;
        return targetMathNetMap.getOrDefault(uni, Map.of()).getOrDefault(dept, ZERO_TARGET_REQUIREMENT).requiredAytNet;
    }

    private double selectedRequiredAverageScore(String uni, String dept) {
        if (uni == null || dept == null) return 0.0;
        return targetMathNetMap.getOrDefault(uni, Map.of()).getOrDefault(dept, ZERO_TARGET_REQUIREMENT).requiredAverageScore;
    }

    private long selectedRequiredRanking(String uni, String dept) {
        if (uni == null || dept == null) return 0L;
        return targetMathNetMap.getOrDefault(uni, Map.of()).getOrDefault(dept, ZERO_TARGET_REQUIREMENT).referenceRanking;
    }

    private static final TargetRequirement ZERO_TARGET_REQUIREMENT = new TargetRequirement(0, 0, 0, 0, 0L);

    private Map<String, List<String>> createWeeklyTopicsMap() {
        Map<String, List<String>> map = new HashMap<>();
        map.put("Matematik", List.of("Temel Kavramlar", "Problemler", "Fonksiyonlar", "Türev", "İntegral"));
        map.put("Türkçe", List.of("Paragraf", "Dil Bilgisi", "Sözcükte Anlam", "Cümlede Anlam"));
        map.put("Fizik", List.of("Hareket", "Kuvvet", "Elektrik", "Optik"));
        map.put("Kimya", List.of("Mol", "Kimyasal Tepkimeler", "Çözeltiler", "Organik Kimya"));
        map.put("Biyoloji", List.of("Hücre", "Kalıtım", "Ekosistem", "İnsan Fizyolojisi"));
        map.put("Tarih", List.of("İlk Çağ", "Osmanlı", "Kurtuluş Savaşı", "İnkılap Tarihi"));
        map.put("Coğrafya", List.of("İklim", "Nüfus", "Harita Bilgisi", "Türkiye Coğrafyası"));
        return map;
    }

    private Map<String, Map<String, TargetRequirement>> createTargetMathNetMap() {
        Map<String, Map<String, TargetRequirement>> map = new LinkedHashMap<>();
        map.put("Boğaziçi Üniversitesi", Map.of(
                "Bilgisayar Mühendisliği", new TargetRequirement(33.0, 108.0, 72.0, 498.0, 892L),
                "Elektrik-Elektronik Mühendisliği", new TargetRequirement(31.0, 102.0, 68.0, 490.0, 2380L)
        ));
        map.put("ODTÜ", Map.of(
                "Bilgisayar Mühendisliği", new TargetRequirement(32.0, 106.0, 70.0, 494.0, 1180L),
                "Makine Mühendisliği", new TargetRequirement(29.0, 98.0, 63.0, 480.0, 4620L)
        ));
        map.put("İTÜ", Map.of(
                "Yazılım Mühendisliği", new TargetRequirement(30.0, 101.0, 66.0, 486.0, 3280L),
                "İnşaat Mühendisliği", new TargetRequirement(26.0, 92.0, 58.0, 462.0, 14200L)
        ));
        map.put("Hacettepe Üniversitesi", Map.of(
                "Tıp", new TargetRequirement(34.0, 112.0, 75.0, 503.0, 420L),
                "Diş Hekimliği", new TargetRequirement(31.0, 104.0, 69.0, 492.0, 1780L)
        ));
        return map;
    }

    private void refreshGoalLessonBreakdown() {
        goalLessonBreakdownContainer.getChildren().clear();
        Map<String, List<Double>> grouped = new LinkedHashMap<>();
        for (MockExam exam : state.exams()) {
            String prefix = exam.getExamType() == null ? "GENEL" : exam.getExamType().name();
            for (LessonResult lesson : exam.getLessonResults()) {
                String key = prefix + " - " + normalizeLessonDisplay(lesson.getLessonName());
                double net = lesson.getCorrect() - lesson.getWrong() / 4.0;
                grouped.computeIfAbsent(key, k -> new ArrayList<>()).add(net);
            }
        }
        grouped.forEach((lesson, values) -> {
            double avg = values.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
            Label row = new Label(lesson + " ortalama net: " + String.format("%.2f", avg));
            row.getStyleClass().add("metric-note");
            goalLessonBreakdownContainer.getChildren().add(row);
        });
    }

    private static class TargetRequirement {
        final double requiredMathNet;
        final double requiredTytNet;
        final double requiredAytNet;
        final double requiredAverageScore;
        /** Örnek Atlas verisi: yaklaşık taban / yerleştirme sırası referansı (küçük daha iyidir). */
        final long referenceRanking;

        TargetRequirement(double requiredMathNet, double requiredTytNet, double requiredAytNet,
                          double requiredAverageScore, long referenceRanking) {
            this.requiredMathNet = requiredMathNet;
            this.requiredTytNet = requiredTytNet;
            this.requiredAytNet = requiredAytNet;
            this.requiredAverageScore = requiredAverageScore;
            this.referenceRanking = referenceRanking;
        }
    }

    private void setAuthStatus(Label label, String text, boolean success) {
        label.setText(text == null ? "" : text);
        label.getStyleClass().removeAll("auth-status-success", "auth-status-error");
        if (text != null && !text.isBlank()) {
            label.getStyleClass().add(success ? "auth-status-success" : "auth-status-error");
        }
    }

    private void showAuthLoginForm() {
        loginFormPane.setVisible(true);
        loginFormPane.setManaged(true);
        registerFormPane.setVisible(false);
        registerFormPane.setManaged(false);
        setAuthStatus(loginStatusLabel, "", true);
        setAuthStatus(registerStatusLabel, "", true);
    }

    private void showAuthRegisterForm() {
        loginFormPane.setVisible(false);
        loginFormPane.setManaged(false);
        registerFormPane.setVisible(true);
        registerFormPane.setManaged(true);
        setAuthStatus(loginStatusLabel, "", true);
        setAuthStatus(registerStatusLabel, "", true);
    }

    private void showAuthScreen(boolean show) {
        authContainer.setVisible(show);
        authContainer.setManaged(show);
        appContentContainer.setVisible(!show);
        appContentContainer.setManaged(!show);
        boolean loggedIn = !show;
        welcomeLabel.setVisible(loggedIn && !sidebarCollapsed);
        welcomeLabel.setManaged(loggedIn && !sidebarCollapsed);
        logoutButton.setVisible(loggedIn);
        logoutButton.setManaged(loggedIn);
    }

    private int parseIntOrZero(String value) {
        if (value == null || value.isBlank()) return 0;
        return Integer.parseInt(value.trim());
    }

    /** Boş alan 0 sayılır; boş olmayıp sayı değilse anlamlı ileti ile NumberFormatException. */
    private int parseExamPositiveIntField(TextField field, String fieldDescription) throws NumberFormatException {
        String raw = field.getText();
        if (raw == null || raw.isBlank()) {
            return 0;
        }
        String trimmed = raw.trim();
        try {
            int v = Integer.parseInt(trimmed);
            if (v < 0) {
                throw new NumberFormatException(fieldDescription + ": Negatif olamaz (girilen: " + v + ").");
            }
            return v;
        } catch (NumberFormatException ex) {
            if (ex.getMessage() != null && ex.getMessage().contains("Negatif olamaz")) {
                throw ex;
            }
            throw new NumberFormatException(fieldDescription + ": Gecersiz sayi (\"" + trimmed + "\"). Sadece 0 veya pozitif tam sayi girin.");
        }
    }

    private double parseDouble(TextField field) {
        return Double.parseDouble(field.getText().trim());
    }

    private String safeText(TextField field) {
        return field.getText() == null ? "" : field.getText().trim();
    }
}
