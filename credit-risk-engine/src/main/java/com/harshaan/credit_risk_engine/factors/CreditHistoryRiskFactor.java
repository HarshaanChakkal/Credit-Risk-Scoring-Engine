package com.harshaan.credit_risk_engine.factors;

import com.harshaan.credit_risk_engine.model.RiskFactorScore;
import com.harshaan.credit_risk_engine.model.LoanApplication;;
public class CreditHistoryRiskFactor implements RiskFactor{
    @Override
    public RiskFactorScore evaluate(LoanApplication app) {
        int months = app.creditHistoryMonths();
        int delinq = app.delinquencies12mo();

        int base;
        if (months >= 120) base = 100;
        else if (months >= 60) base = 80;
        else if (months >= 24) base = 60;
        else base = 40;

        // penalty: -15 per delinquency in last 12 months
        int score = base - (15 * delinq);

        // clamp to 0..100
        if (score < 0) score = 0;
        if (score > 100) score = 100;

        String reason = null;
        if (delinq > 0) {
            reason = "Recent delinquencies reduce creditworthiness";
        } else if (months >= 60) {
            reason = "Longer credit history improves risk confidence";
        }

        return new RiskFactorScore("CREDIT_HISTORY", score, reason);
    }
}
