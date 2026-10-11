package com.alperenakademi.yks.service;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class CountdownService {
    private Timeline timeline;

    public void start(LocalDate yksDate, Consumer<String> onTick) {
        start(yksDate, (text, ignored) -> onTick.accept(text));
    }

    public void start(LocalDate yksDate, BiConsumer<String, Long> onTick) {
        stop();
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime exam = yksDate.atTime(10, 15);
            long seconds = ChronoUnit.SECONDS.between(now, exam);
            if (seconds <= 0) {
                onTick.accept("Sınav başladı.", 0L);
                stop();
                return;
            }
            long days = seconds / 86_400;
            long hours = (seconds % 86_400) / 3_600;
            long mins = (seconds % 3_600) / 60;
            long secs = seconds % 60;
            onTick.accept(String.format("%d gün %02d:%02d:%02d", days, hours, mins, secs), seconds);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void stop() {
        if (timeline != null) {
            timeline.stop();
        }
    }
}
