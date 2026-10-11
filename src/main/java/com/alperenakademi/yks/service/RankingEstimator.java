package com.alperenakademi.yks.service;

/**
 * Dahili tahmini deneme puanını, kayıtta tutulan Atlas referans (son yıl taban puana yakın gösterge)
 * ile hizalayarak yaklaşık bir yerleştirme sırası göstergesi üretir. Gerçek ÖSYM sıralaması değildir.
 */
public final class RankingEstimator {
    /** Puan farkına duyarlılık; referansa göre yaklaşık sapma için. */
    private static final double BETA = 0.042;

    private static final long MIN_RANK = 1L;
    private static final long MAX_RANK = 2_900_000L;

    private RankingEstimator() {
    }

    /**
     * Öğrencinin son deneme puanına göre, verilen referans noktasını merkeze alarak tahmini sıra.
     *
     * @param studentEstimatedScore Bu uygulamadaki {@code ScoreCalculatorService} çıktısı
     * @param referenceEstimatedScore Atlas’tan türetilen hedef programa ait yaklaşık referans deneme göstergesi
     * @param referenceRank         Aynı yıl/program için saklanan referans taban sıra ( küçük = daha iyi )
     */
    public static long estimateRankFromReference(double studentEstimatedScore,
                                                double referenceEstimatedScore,
                                                long referenceRank) {
        if (referenceRank <= 0 || referenceEstimatedScore <= 0) {
            return -1;
        }
        double raw = referenceRank * Math.exp(-BETA * (studentEstimatedScore - referenceEstimatedScore));
        long rounded = Math.round(raw);
        return Math.min(MAX_RANK, Math.max(MIN_RANK, rounded));
    }
}
