package com.harshaan.credit_risk_engine.model;
import java.math.BigDecimal;

public record LoanApplication(
    BigDecimal incomeAnnual,
    BigDecimal monthlyDebtPayments,
    BigDecimal creditUtilizationPct,
    int creditHistoryMonths,
    int delinquencies12mo,
    BigDecimal employmentYears,
    BigDecimal loanAmount,
    int loanTermMonths
) {}
