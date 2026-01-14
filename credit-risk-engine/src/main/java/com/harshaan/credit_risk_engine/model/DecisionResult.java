package com.harshaan.credit_risk_engine.model;

import java.util.List;

public record DecisionResult(
    double weightedScore,
    int finalScore,
    Decision decision,
    List<RiskFactorScore> factors,
    List<String> reasons
) {}
