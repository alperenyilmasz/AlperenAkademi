package com.alperenakademi.yks.service;

import com.alperenakademi.yks.model.ExamType;

import java.util.Map;

/**
 * ÖSYM YKS sınavlarındaki yaygın soru dağılımlarına göre ders başına soru üst limiti.
 * Bu uygulamadaki ders birleştirmeleri (TYT: Sosyal, Fen tek test; AYT: SAY sınavı yaygın dağılımı).
 */
public final class ExamInputLimits {

    /** TYT: Türkçe 40, Mat 40, Sosyal 20, Fen 20 (toplam 120). */
    private static final Map<String, Integer> TYT_BY_LESSON = Map.of(
            "Türkçe", 40,
            "Matematik", 40,
            "Sosyal", 20,
            "Fen", 20
    );

    /** AYT SAY: Matematik 40; Fen 40 (14+13+13); Edebiyat 24; TR1 10+6 */
    private static final Map<String, Integer> AYT_BY_LESSON = Map.of(
            "Matematik", 40,
            "Fizik", 14,
            "Kimya", 13,
            "Biyoloji", 13,
            "Edebiyat", 24,
            "Tarih", 10,
            "Coğrafya", 6
    );

    private ExamInputLimits() {
    }

    public static int maxQuestions(ExamType type, String lessonName) {
        if (lessonName == null || lessonName.isBlank()) {
            return 0;
        }
        String key = normalizeLessonName(lessonName);
        Map<String, Integer> map = type == ExamType.AYT ? AYT_BY_LESSON : TYT_BY_LESSON;
        return map.getOrDefault(key, 0);
    }

    private static String normalizeLessonName(String lessonName) {
        String key = lessonName.trim();
        return switch (key) {
            case "Turkce" -> "Türkçe";
            case "Cografya" -> "Coğrafya";
            default -> key;
        };
    }

    /**
     * @return Türkçe hata iletisi ya da uygun ise {@code null}
     */
    public static String validationErrorTurkish(ExamType type, String lessonName, int correct, int wrong) {
        int max = maxQuestions(type, lessonName);
        if (max <= 0) {
            return "Bilinmeyen ders: " + lessonName;
        }
        if (correct < 0 || wrong < 0) {
            return lessonName + ": Doğru ve yanlış sayısı negatif olamaz.";
        }
        if (correct > max) {
            return lessonName + ": Doğru en fazla " + max + " olabilir (TYT/AYT soru limiti).";
        }
        if (wrong > max) {
            return lessonName + ": Yanlış en fazla " + max + " olabilir (TYT/AYT soru limiti).";
        }
        if (correct + wrong > max) {
            return lessonName + ": Doğru + yanlış toplamı " + max + " soruyu geçemez (Bu derste "
                    + max + " soru vardır).";
        }
        return null;
    }
}
