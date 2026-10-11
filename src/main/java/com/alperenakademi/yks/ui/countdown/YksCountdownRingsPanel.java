package com.alperenakademi.yks.ui.countdown;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Dört birim için dairesel YKS geri sayım bileşeni (gün–saat–dakika–saniye).
 * Progress: gün için (kalan süre gün birimi) / (takvim gün aralığı); saat 24, dakika ve saniye 60.
 */
public final class YksCountdownRingsPanel extends HBox {

    private static final Color DAY_ACCENT = Color.web("#EAB308");
    private static final Color HOUR_ACCENT = Color.web("#2563EB");
    private static final Color MINUTE_ACCENT = Color.web("#22C55E");
    private static final Color SECOND_ACCENT = Color.web("#06B6D4");

    private final CircularCountdownRing dayRing;
    private final CircularCountdownRing hourRing;
    private final CircularCountdownRing minuteRing;
    private final CircularCountdownRing secondRing;

    private boolean english;

    public YksCountdownRingsPanel(boolean english) {
        this.english = english;
        dayRing = new CircularCountdownRing(unitTitle("DAY", "GÜN"), DAY_ACCENT);
        hourRing = new CircularCountdownRing(unitTitle("HOUR", "SAAT"), HOUR_ACCENT);
        minuteRing = new CircularCountdownRing(unitTitle("MINUTE", "DAKİKA"), MINUTE_ACCENT);
        secondRing = new CircularCountdownRing(unitTitle("SECOND", "SANİYE"), SECOND_ACCENT);

        setSpacing(28);
        setAlignment(Pos.CENTER);
        for (CircularCountdownRing r : new CircularCountdownRing[] { dayRing, hourRing, minuteRing, secondRing }) {
            HBox.setHgrow(r, Priority.ALWAYS);
            r.setMaxWidth(160);
        }
        getChildren().addAll(dayRing, hourRing, minuteRing, secondRing);
        getStyleClass().add("yks-countdown-rings-panel");
        setMaxWidth(Double.MAX_VALUE);

        update(0L, LocalDate.now());
    }

    public void setEnglish(boolean english) {
        this.english = english;
        refreshTitles();
    }

    private void refreshTitles() {
        dayRing.setTitle(unitTitle("DAY", "GÜN"));
        hourRing.setTitle(unitTitle("HOUR", "SAAT"));
        minuteRing.setTitle(unitTitle("MINUTE", "DAKİKA"));
        secondRing.setTitle(unitTitle("SECOND", "SANİYE"));
    }

    private String unitTitle(String en, String tr) {
        return english ? en : tr;
    }

    /** @param yksExamDate CountdownService ile aynı {@link LocalDate} */
    public void update(long secondsRemaining, LocalDate yksExamDate) {
        LocalDate today = LocalDate.now();

        if (secondsRemaining <= 0) {
            dayRing.setValueText("0");
            hourRing.setValueText("00");
            minuteRing.setValueText("00");
            secondRing.setValueText("00");
            dayRing.setProgress(0);
            hourRing.setProgress(0);
            minuteRing.setProgress(0);
            secondRing.setProgress(0);
            return;
        }

        long days = secondsRemaining / 86_400;
        long hours = (secondsRemaining % 86_400) / 3_600;
        long minutes = (secondsRemaining % 3_600) / 60;
        long secs = secondsRemaining % 60;

        dayRing.setValueText(String.valueOf(days));
        hourRing.setValueText(String.format("%02d", hours));
        minuteRing.setValueText(String.format("%02d", minutes));
        secondRing.setValueText(String.format("%02d", secs));

        double dayFractionRemain = secondsRemaining / 86_400.0;
        long calendarDaysGap = ChronoUnit.DAYS.between(today, yksExamDate);
        double dayDenominator = calendarDaysGap <= 0 ? 1.0 : (double) calendarDaysGap;
        double dayProgress = Math.min(1.0, dayFractionRemain / dayDenominator);

        dayRing.setProgress(dayProgress);
        hourRing.setProgress(hours / 24.0);
        minuteRing.setProgress(minutes / 60.0);
        secondRing.setProgress(secs / 60.0);
    }
}
