package com.harshaan.credit_risk_engine.factors;

import java.math.BigDecimal;

import com.harshaan.credit_risk_engine.model.LoanApplication;
import com.harshaan.credit_risk_engine.model.RiskFactorScore;
import com.harshaan.credit_risk_engine.util.FinancialCalculations;

public class DtiRiskFactor implements RiskFactor {
    @Override
    public RiskFactorScore evaluate(LoanApplication app) {
        BigDecimal dti = FinancialCalculations.dti(app.incomeAnnual(), app.monthlyDebtPayments());
        int score;

        if (dti.compareTo(new BigDecimal("0.20")) <= 0) score = 100;
        else if (dti.compareTo(new BigDecimal("0.35")) <= 0) score = 80;
        else if (dti.compareTo(new BigDecimal("0.50")) <= 0) score = 60;
        else if (dti.compareTo(new BigDecimal("0.65")) <= 0) score = 40;
        else score = 20;

        String reason = null;
        
        if (dti.compareTo(new BigDecimal("0.50")) > 0) {
            reason = "High debt-to-income ratio increases risk";
        } else if (dti.compareTo(new BigDecimal("0.35")) <= 0) {
            reason = "Healthy debt-to-income ratio lowers risk";
        }

        return new RiskFactorScore("DTI", score, reason);
    }
}
