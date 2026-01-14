package com.harshaan.credit_risk_engine.engine;

import com.harshaan.credit_risk_engine.model.Decision;
import com.harshaan.credit_risk_engine.model.DecisionResult;
import com.harshaan.credit_risk_engine.model.LoanApplication;
import com.harshaan.credit_risk_engine.model.RiskFactorScore;
import com.harshaan.credit_risk_engine.factors.CreditHistoryRiskFactor;
import com.harshaan.credit_risk_engine.factors.DtiRiskFactor;
import com.harshaan.credit_risk_engine.factors.EmploymentRiskFactor;
import com.harshaan.credit_risk_engine.factors.IncomeCushionRiskFactor;
import com.harshaan.credit_risk_engine.factors.RiskFactor;
import com.harshaan.credit_risk_engine.factors.UtilizationRiskFactor;

import java.util.ArrayList;
import java.util.List;

public class RuleBasedCreditRiskEngine implements CreditRiskEngine {
    
    private final RiskFactor creditHistoryFactor;
    private final RiskFactor dtiFactor;
    private final RiskFactor incomeCushionFactor;
    private final RiskFactor utilizationFactor;
    private final RiskFactor employmentFactor;

    public RuleBasedCreditRiskEngine(
            RiskFactor creditHistoryFactor,
            RiskFactor dtiFactor,
            RiskFactor incomeCushionFactor,
            RiskFactor utilizationFactor,
            RiskFactor employmentFactor
    ) {
        this.creditHistoryFactor = creditHistoryFactor;
        this.dtiFactor = dtiFactor;
        this.incomeCushionFactor = incomeCushionFactor;
        this.utilizationFactor = utilizationFactor;
        this.employmentFactor = employmentFactor;
    }

    @Override
    public DecisionResult assess(LoanApplication app) {
        List<RiskFactorScore> factors = new ArrayList<>();

        RiskFactorScore creditHistory = creditHistoryFactor.evaluate(app);
        RiskFactorScore dti = dtiFactor.evaluate(app);
        RiskFactorScore incomeCushion = incomeCushionFactor.evaluate(app);
        RiskFactorScore utilization = utilizationFactor.evaluate(app);
        RiskFactorScore employment = employmentFactor.evaluate(app);

        factors.add(creditHistory);
        factors.add(dti);
        factors.add(incomeCushion);
        factors.add(utilization);
        factors.add(employment);

        double weighted =
                ScorecardConfig.WEIGHT_CREDIT_HISTORY * creditHistory.score()
                        + ScorecardConfig.WEIGHT_DTI * dti.score()
                        + ScorecardConfig.WEIGHT_INCOME_CUSHION * incomeCushion.score()
                        + ScorecardConfig.WEIGHT_UTILIZATION * utilization.score()
                        + ScorecardConfig.WEIGHT_EMPLOYMENT * employment.score();

        int finalScore = ScorecardConfig.mapToFinalScore(weighted);

        Decision decision;
        if (finalScore >= 650) decision = Decision.APPROVE;
        else if (finalScore >= 550) decision = Decision.MANUAL_REVIEW;
        else decision = Decision.REJECT;

        List<String> reasons = factors.stream()
                .map(RiskFactorScore::reason)
                .filter(r -> r != null && !r.isBlank())
                .toList();

        return new DecisionResult(weighted, finalScore, decision, factors, reasons);
    }
    public static RuleBasedCreditRiskEngine defaultEngine() {
        return new RuleBasedCreditRiskEngine(
                new CreditHistoryRiskFactor(),
                new DtiRiskFactor(),
                new IncomeCushionRiskFactor(),
                new UtilizationRiskFactor(),
                new EmploymentRiskFactor()
        );
    }
}
