package com.harshaan.credit_risk_engine.factors;

import com.harshaan.credit_risk_engine.model.LoanApplication;
import com.harshaan.credit_risk_engine.model.RiskFactorScore;
import com.harshaan.credit_risk_engine.util.FinancialCalculations;

import java.math.BigDecimal;
public class IncomeCushionRiskFactor implements RiskFactor{
    @Override
    public RiskFactorScore evaluate(LoanApplication app) {
        BigDecimal monthlyIncome = FinancialCalculations.monthlyIncome(app.incomeAnnual());
        BigDecimal estPayment = FinancialCalculations.estimatedMonthlyPayment(app.loanAmount(), app.loanTermMonths());
        BigDecimal obligations = app.monthlyDebtPayments().add(estPayment);

        if (monthlyIncome.compareTo(BigDecimal.ZERO) <= 0) {
            return new RiskFactorScore("INCOME_CUSHION", 20, "No verifiable income available");
        }

        // cushion ratio = (income - obligations) / income
        BigDecimal cushion = monthlyIncome.subtract(obligations);
        BigDecimal ratio = cushion.divide(monthlyIncome, 10, java.math.RoundingMode.HALF_UP);

        int score;
        if (ratio.compareTo(new BigDecimal("0.60")) >= 0) score = 100;
        else if (ratio.compareTo(new BigDecimal("0.40")) >= 0) score = 80;
        else if (ratio.compareTo(new BigDecimal("0.20")) >= 0) score = 60;
        else if (ratio.compareTo(new BigDecimal("0.05")) >= 0) score = 40;
        else score = 20;

        String reason = null;
        if (ratio.compareTo(new BigDecimal("0.20")) < 0) {
            reason = "Low monthly income cushion after obligations increases affordability risk";
        } else if (ratio.compareTo(new BigDecimal("0.40")) >= 0) {
            reason = "Strong monthly income cushion lowers affordability risk";
        }

        return new RiskFactorScore("INCOME_CUSHION", score, reason);
    }
}
