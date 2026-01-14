# Golden Test Cases (v1)

## Case 1 — Strong applicant (expected APPROVE)
incomeAnnual=120000
monthlyDebtPayments=1000
creditUtilizationPct=15
creditHistoryMonths=96
delinquencies12mo=0
employmentYears=4
loanAmount=12000
loanTermMonths=36

Notes:
- Should have strong DTI, strong credit history, low utilization.

## Case 2 — Borderline (expected MANUAL_REVIEW)
incomeAnnual=60000
monthlyDebtPayments=1700
creditUtilizationPct=55
creditHistoryMonths=24
delinquencies12mo=1
employmentYears=1.2
loanAmount=15000
loanTermMonths=36

## Case 3 — High risk (expected REJECT)
incomeAnnual=35000
monthlyDebtPayments=2000
creditUtilizationPct=85
creditHistoryMonths=8
delinquencies12mo=3
employmentYears=0.5
loanAmount=18000
loanTermMonths=24
