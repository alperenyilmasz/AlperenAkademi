package com.alperenakademi.yks.service;

import com.alperenakademi.yks.model.ExamType;
import com.alperenakademi.yks.model.MockExam;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Son TYT ve son AYT deneme tahmini puanları ile OBP (1-100) değerini birleştirerek
 * yerleştirme hedef kartında kullanılan tek bir yaklaşık gösterge puan üretir.
 * Gerçek ÖSYM yerleştirme puanı ile aynı değildir.
 */
public final class PlacementProxyService {

    /** SAY benzeri ağırlık: AYT daha baskın. */
    private static final double WEIGHT_TYT = 0.40;
    private static final double WEIGHT_AYT = 0.60;
    /** OBP orta değer (50) etrafında yaklaşık puan sapması ölçeği. */
    private static final double OBP_POINT_SCALE = 0.52;

    private PlacementProxyService() {
    }

    public static Optional<MockExam> newestExamOfType(List<MockExam> exams, ExamType type) {
        if (exams == null || exams.isEmpty()) {
            return Optional.empty();
        }
        return exams.stream()
                .filter(e -> e.getExamType() == type)
                .sorted(Comparator.comparing(MockExam::getDate, Comparator.nullsFirst(Comparator.naturalOrder())).reversed())
                .findFirst();
    }

    /**
     * @param tytEstimated son TYT denemenin {@link com.alperenakademi.yks.service.ScoreCalculatorService} çıktısı; yoksa 0
     * @param aytEstimated son AYT çıktısı; yoksa 0
     * @param obp          1-100 (kullanıcı girişi). Geçersiz ise 50 kabul edilir.
     */
    public static double combinedPlacementProxy(double tytEstimated, double aytEstimated, int obp) {
        int o = (obp >= 1 && obp <= 100) ? obp : 50;
        double examPart;
        if (tytEstimated > 0 && aytEstimated > 0) {
            examPart = WEIGHT_TYT * tytEstimated + WEIGHT_AYT * aytEstimated;
        } else if (aytEstimated > 0) {
            examPart = aytEstimated;
        } else {
            examPart = tytEstimated;
        }
        double obpDelta = (o - 50.0) * OBP_POINT_SCALE;
        return Math.max(0.0, examPart + obpDelta);
    }
}
