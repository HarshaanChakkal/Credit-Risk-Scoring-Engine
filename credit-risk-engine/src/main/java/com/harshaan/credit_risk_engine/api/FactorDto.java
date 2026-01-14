package com.harshaan.credit_risk_engine.api;

public record FactorDto(
    String factorName,
    int score,
    String reason
) {}
