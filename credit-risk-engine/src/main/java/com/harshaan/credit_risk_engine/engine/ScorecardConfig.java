package com.harshaan.credit_risk_engine.engine;

public final class ScorecardConfig {
    private ScorecardConfig() {}

    public static final double WEIGHT_CREDIT_HISTORY = 0.30;
    public static final double WEIGHT_DTI = 0.25;
    public static final double WEIGHT_INCOME_CUSHION = 0.20;
    public static final double WEIGHT_UTILIZATION = 0.15;
    public static final double WEIGHT_EMPLOYMENT = 0.10;

    public static int mapToFinalScore(double weightedScore0to100) {
        // 300 + round(S * 5.5)
        return 300 + (int) Math.round(weightedScore0to100 * 5.5);
    }
}
