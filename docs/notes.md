# Rule Based Credit Risk Scoring Engine

## 1) Inputs (Loan Application)
- incomeAnnual >= 0
- monthlyDebtPayments >= 0
- creditUtilizationPct (0 - 100)
- creditHistoryMonths >= 0
- delinquencies12mo >= 0
- employmentYears >= 0
- loanAmount (USD) > 0
- loanTermMonths > 0

## 2) Derived Metrics
- monthlyIncome = incomeAnnual / 12
- dti = monthlyDebtPayments / monthlyIncome
  - If monthlyIncome == 0 and monthlyDebtPayments > 0 => dti = 1.0 (max risk)
  - If monthlyIncome == 0 and monthlyDebtPayments == 0 => dti = 0.0

## 3) Risk Factors (each outputs 0–100)
### A) DTI Score (weight 0.25)
- dti <= 0.20 => 100
- 0.20 < dti <= 0.35 => 80
- 0.35 < dti <= 0.50 => 60
- 0.50 < dti <= 0.65 => 40
- dti > 0.65 => 20

Reason triggers:
- dti > 0.50 => "High debt-to-income ratio increases risk"
- dti <= 0.35 => "Healthy debt-to-income ratio lowers risk"

### B) Credit Utilization Score (weight 0.15)
- util <= 10 => 100
- 10 < util <= 30 => 85
- 30 < util <= 50 => 65
- 50 < util <= 75 => 45
- util > 75 => 25

Reason triggers:
- util > 50 => "High credit utilization increases risk"
- util <= 30 => "Low credit utilization lowers risk"

### C) Credit History Length Score (weight 0.30)
- months >= 84 => 100
- 36–83 => 80
- 12–35 => 60
- < 12 => 40

Reason triggers:
- months < 12 => "Very short credit history increases risk"
- months >= 36 => "Established credit history lowers risk"

### D) Delinquency Penalty (applied inside Credit History Score)
- delinquencies12mo == 0 => no penalty
- 1 => -10 points
- 2 => -20 points
- >=3 => -35 points
Clamp final credit history factor score to [0, 100].

Reason triggers:
- delinquencies12mo >= 1 => "Recent delinquencies increase risk"

### E) Employment Stability Score (weight 0.10)
- years >= 5 => 100
- 2–4.99 => 80
- 1–1.99 => 60
- < 1 => 40

Reason triggers:
- years < 1 => "Limited employment history increases risk"
- years >= 2 => "Stable employment lowers risk"

### F) Income Cushion Score (weight 0.20)
Use monthlyIncome vs estimated monthly payment.
Estimated payment approximation:
- estPayment = loanAmount / loanTermMonths (simple approximation for v1)
paymentToIncome = estPayment / monthlyIncome
If monthlyIncome == 0 => score 20.

Score:
- pti <= 0.10 => 100
- 0.10 < pti <= 0.20 => 80
- 0.20 < pti <= 0.30 => 60
- 0.30 < pti <= 0.45 => 40
- pti > 0.45 => 20

Reason triggers:
- pti > 0.30 => "Loan payment is high relative to income"
- pti <= 0.20 => "Loan payment is manageable relative to income"

## 4) Final Score
score =
  0.30 * creditHistoryScore
+ 0.25 * dtiScore
+ 0.20 * incomeCushionScore
+ 0.15 * utilizationScore
+ 0.10 * employmentScore

finalScore = 300 + (score * 5.5)

Round finalScore to nearest integer.

## 5) Decision Bands
- finalScore >= 650 => APPROVE
- 550 <= finalScore < 650 => MANUAL_REVIEW
- finalScore < 550 => REJECT

## 6) Explainability Output
Return:
- factor scores + finalScore + decision + reasons (list of strings)
Include only the reasons whose conditions are triggered.