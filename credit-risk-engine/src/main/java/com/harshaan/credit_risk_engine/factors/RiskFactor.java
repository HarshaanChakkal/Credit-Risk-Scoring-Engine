package com.harshaan.credit_risk_engine.factors;

import com.harshaan.credit_risk_engine.model.LoanApplication;
import com.harshaan.credit_risk_engine.model.RiskFactorScore;

public interface RiskFactor {
    RiskFactorScore evaluate(LoanApplication app);
}
