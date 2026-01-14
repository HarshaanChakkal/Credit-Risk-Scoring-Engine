package com.harshaan.credit_risk_engine.engine;


import com.harshaan.credit_risk_engine.factors.RiskFactor;
import com.harshaan.credit_risk_engine.model.Decision;
import com.harshaan.credit_risk_engine.model.LoanApplication;
import com.harshaan.credit_risk_engine.model.RiskFactorScore;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;



class RuleBasedCreditRiskEngineTest {
    @Test
    void strong_applicant_should_approve() {
        var engine = RuleBasedCreditRiskEngine.defaultEngine();

        LoanApplication app = new LoanApplication(
                new BigDecimal("120000"),
                new BigDecimal("1000"),
                new BigDecimal("15"),
                96,
                0,
                new BigDecimal("4"),
                new BigDecimal("12000"),
                36
        );

        var result = engine.assess(app);
        assertEquals(Decision.APPROVE, result.decision());
    }
}
