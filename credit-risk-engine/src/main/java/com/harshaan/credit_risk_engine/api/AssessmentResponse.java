package com.harshaan.credit_risk_engine.api;
import com.harshaan.credit_risk_engine.model.Decision;
import java.util.List;
import java.math.BigDecimal;

public record AssessmentResponse(
    BigDecimal weightedScore,
    int finalScore,
    Decision decision,
    List<FactorDto> factors,
    List<String> reasons
) {}
