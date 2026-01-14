package com.harshaan.credit_risk_engine.engine;

import com.harshaan.credit_risk_engine.model.DecisionResult;
import com.harshaan.credit_risk_engine.model.LoanApplication;

public interface CreditRiskEngine {
    DecisionResult assess(LoanApplication application);
}
