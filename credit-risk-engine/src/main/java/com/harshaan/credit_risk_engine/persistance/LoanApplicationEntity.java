package com.harshaan.credit_risk_engine.persistance;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "loan_applications")
public class LoanApplicationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="income_annual", nullable=false)
    private BigDecimal incomeAnnual;

    @Column(name="monthly_debt_payments", nullable=false)
    private BigDecimal monthlyDebtPayments;

    @Column(name="credit_utilization_pct", nullable=false)
    private BigDecimal creditUtilizationPct;

    @Column(name="credit_history_months", nullable=false)
    private int creditHistoryMonths;

    @Column(name="delinquencies_12mo", nullable=false)
    private int delinquencies12mo;

    @Column(name="employment_years", nullable=false)
    private BigDecimal employmentYears;

    @Column(name="loan_amount", nullable=false)
    private BigDecimal loanAmount;

    @Column(name="loan_term_months", nullable=false)
    private int loanTermMonths;

    @Column(name="created_at", nullable=false)
    private OffsetDateTime createdAt;

    // getters and setters
    public LoanApplicationEntity() {}

    public Long getId() { return id; }
    public BigDecimal getIncomeAnnual() { return incomeAnnual; }
    public void setIncomeAnnual(BigDecimal incomeAnnual) { this.incomeAnnual = incomeAnnual;}
    
    public BigDecimal getMonthlyDebtPayments() { return monthlyDebtPayments;}
    public void setMonthlyDebtPayments(BigDecimal monthlyDebtPayments) { this.monthlyDebtPayments = monthlyDebtPayments; }

    public BigDecimal getCreditUtilizationPct() { return creditUtilizationPct; }
    public void setCreditUtilizationPct(BigDecimal creditUtilizationPct) { this.creditUtilizationPct = creditUtilizationPct; }

    public int getCreditHistoryMonths() { return creditHistoryMonths; }
    public void setCreditHistoryMonths(int creditHistoryMonths) { this.creditHistoryMonths = creditHistoryMonths; }

    public int getDelinquencies12mo() { return delinquencies12mo; }
    public void setDelinquencies12mo(int delinquencies12mo) { this.delinquencies12mo = delinquencies12mo; }

    public BigDecimal getEmploymentYears() { return employmentYears; }
    public void setEmploymentYears(BigDecimal employmentYears) { this.employmentYears = employmentYears; }

    public BigDecimal getLoanAmount() { return loanAmount; }
    public void setLoanAmount(BigDecimal loanAmount) { this.loanAmount = loanAmount; }

    public int getLoanTermMonths() { return loanTermMonths; }
    public void setLoanTermMonths(int loanTermMonths) { this.loanTermMonths = loanTermMonths; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
