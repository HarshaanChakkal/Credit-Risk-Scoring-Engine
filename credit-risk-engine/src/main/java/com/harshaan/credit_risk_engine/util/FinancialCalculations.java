package com.harshaan.credit_risk_engine.util;
import java.math.BigDecimal;
import java.math.RoundingMode;

public final class FinancialCalculations {
    private FinancialCalculations() {}

    public static BigDecimal monthlyIncome(BigDecimal incomeAnnual) {
        if (incomeAnnual == null) {
            return BigDecimal.ZERO;
        }
        return incomeAnnual.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);
    }
    // Debt-to-Income Ratio
    public static BigDecimal dti(BigDecimal incomeAnnual, BigDecimal monthlyDebtPayments) {
        BigDecimal monthlyIncome = monthlyIncome(incomeAnnual);
        if (monthlyIncome.compareTo(BigDecimal.ZERO) == 0) {
            return (monthlyDebtPayments.compareTo(BigDecimal.ZERO) > 0)
                    ? BigDecimal.ONE
                    : BigDecimal.ZERO;
        }
        return monthlyDebtPayments.divide(monthlyIncome, 10, RoundingMode.HALF_UP);
    }
    public static BigDecimal estimatedMonthlyPayment(BigDecimal loanAmount, int loanTermMonths) {
        if (loanAmount == null || loanTermMonths <= 0) return BigDecimal.ZERO;
        return loanAmount.divide(BigDecimal.valueOf(loanTermMonths), 10, RoundingMode.HALF_UP);
    }
    
}
