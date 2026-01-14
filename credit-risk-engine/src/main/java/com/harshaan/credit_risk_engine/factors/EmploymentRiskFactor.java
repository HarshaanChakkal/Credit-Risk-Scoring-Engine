package com.harshaan.credit_risk_engine.factors;

import java.math.BigDecimal;

import com.harshaan.credit_risk_engine.model.LoanApplication;
import com.harshaan.credit_risk_engine.model.RiskFactorScore;

public class EmploymentRiskFactor implements RiskFactor {
    @Override
    public RiskFactorScore evaluate(LoanApplication app) {
        BigDecimal years = app.employmentYears();

        int score;
        if (years.compareTo(new BigDecimal("5")) >= 0) score = 100;
        else if (years.compareTo(new BigDecimal("2")) >= 0) score = 80;
        else if (years.compareTo(new BigDecimal("1")) >= 0) score = 60;
        else score = 35;

        String reason = null;
        if (years.compareTo(new BigDecimal("1")) < 0) {
            reason = "Short employment history may increase income stability risk";
        } else if (years.compareTo(new BigDecimal("2")) >= 0) {
            reason = "Stable employment history lowers income stability risk";
        }

        return new RiskFactorScore("EMPLOYMENT", score, reason);
    }
    
}
