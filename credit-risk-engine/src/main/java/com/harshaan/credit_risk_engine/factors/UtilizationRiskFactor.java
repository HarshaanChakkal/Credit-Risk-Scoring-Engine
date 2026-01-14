package com.harshaan.credit_risk_engine.factors;
import com.harshaan.credit_risk_engine.model.LoanApplication;
import com.harshaan.credit_risk_engine.model.RiskFactorScore;

import java.math.BigDecimal;
public class UtilizationRiskFactor implements RiskFactor {
    @Override
    public RiskFactorScore evaluate(LoanApplication app) {
        BigDecimal util = app.creditUtilizationPct(); // 0..100

        int score;
        if (util.compareTo(new BigDecimal("10")) <= 0) score = 100;
        else if (util.compareTo(new BigDecimal("30")) <= 0) score = 85;
        else if (util.compareTo(new BigDecimal("50")) <= 0) score = 65;
        else if (util.compareTo(new BigDecimal("75")) <= 0) score = 40;
        else score = 20;

        String reason = null;
        if (util.compareTo(new BigDecimal("50")) > 0) {
            reason = "High credit utilization suggests higher revolving debt risk";
        } else if (util.compareTo(new BigDecimal("30")) <= 0) {
            reason = "Low credit utilization suggests responsible credit usage";
        }

        return new RiskFactorScore("UTILIZATION", score, reason);
    }
}
