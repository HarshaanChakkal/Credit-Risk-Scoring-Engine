CREATE TABLE loan_applications (
    id BIGSERIAL PRIMARY KEY,
    income_annual NUMERIC(12, 2) NOT NULL,
    monthly_debt_payments NUMERIC(12, 2) NOT NULL,
    credit_utilization_pct NUMERIC(5,2) NOT NULL,
    credit_history_months INT NOT NULL,
    delinquencies_12mo INT NOT NULL,
    employment_years NUMERIC(5,2) NOT NULL,
    loan_amount NUMERIC(12,2) NOT NULL,
    loan_term_months INT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE risk_assessments (
    id BIGSERIAL PRIMARY KEY,
    loan_application_id BIGINT NOT NULL REFERENCES loan_applications(id),
    weighted_score NUMERIC(5, 2) NOT NULL,
    final_score INT NOT NULL,
    decision VARCHAR(20) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE risk_factor_scores (
  id BIGSERIAL PRIMARY KEY,
  risk_assessment_id BIGINT NOT NULL REFERENCES risk_assessments(id),
  factor_name VARCHAR(50) NOT NULL,
  factor_score INT NOT NULL,
  reason VARCHAR(255)
);
CREATE INDEX idx_risk_assessments_app_id ON risk_assessments(loan_application_id);
CREATE INDEX idx_factor_scores_assessment_id ON risk_factor_scores(risk_assessment_id);
