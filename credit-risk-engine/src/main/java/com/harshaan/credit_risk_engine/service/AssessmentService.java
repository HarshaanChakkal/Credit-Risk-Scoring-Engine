package com.harshaan.credit_risk_engine.service;

import com.harshaan.credit_risk_engine.api.FactorDto;
import com.harshaan.credit_risk_engine.api.AssessmentResponse;
import com.harshaan.credit_risk_engine.engine.RuleBasedCreditRiskEngine;
import com.harshaan.credit_risk_engine.model.Decision;
import com.harshaan.credit_risk_engine.model.DecisionResult;
import com.harshaan.credit_risk_engine.model.LoanApplication;
import com.harshaan.credit_risk_engine.persistance.LoanApplicationEntity;
import com.harshaan.credit_risk_engine.persistance.LoanApplicationRepository;
import com.harshaan.credit_risk_engine.persistance.RiskAssessmentEntity;
import com.harshaan.credit_risk_engine.persistance.RiskAssessmentRepository;
import com.harshaan.credit_risk_engine.persistance.RiskFactorScoreEntity;
import com.harshaan.credit_risk_engine.persistance.RiskFactorScoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class AssessmentService {

    private final LoanApplicationRepository loanRepo;
    private final RiskAssessmentRepository assessmentRepo;
    private final RiskFactorScoreRepository factorRepo;
    private final RuleBasedCreditRiskEngine engine;

    public AssessmentService(
            LoanApplicationRepository loanRepo,
            RiskAssessmentRepository assessmentRepo,
            RiskFactorScoreRepository factorRepo
    ) {
        this.loanRepo = loanRepo;
        this.assessmentRepo = assessmentRepo;
        this.factorRepo = factorRepo;
        this.engine = RuleBasedCreditRiskEngine.defaultEngine();
    }

    @Transactional
    public AssessmentResponse assessOrGetLatest(long applicationId) {
        // 1) If assessment already exists, return it (idempotency)
        return assessmentRepo.findTopByLoanApplicationIdOrderByCreatedAtDesc(applicationId)
                .map(this::toResponseFromExisting)
                .orElseGet(() -> assessAndPersist(applicationId));
    }

    @Transactional(readOnly = true)
    public AssessmentResponse getLatest(long applicationId) {
        RiskAssessmentEntity ra = assessmentRepo.findTopByLoanApplicationIdOrderByCreatedAtDesc(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("No assessment found for application: " + applicationId));

        return toResponseFromExisting(ra);
    }

    private AssessmentResponse assessAndPersist(long applicationId) {
        LoanApplicationEntity e = loanRepo.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found: " + applicationId));
        
        // Map DB -> domain
        LoanApplication app = new LoanApplication(
                e.getIncomeAnnual(),
                e.getMonthlyDebtPayments(),
                e.getCreditUtilizationPct(),
                e.getCreditHistoryMonths(),
                e.getDelinquencies12mo(),
                e.getEmploymentYears(),
                e.getLoanAmount(),
                e.getLoanTermMonths()
        );

        // Run engine
        DecisionResult result = engine.assess(app);

        // Persist assessment summary
        RiskAssessmentEntity ra = new RiskAssessmentEntity();
        ra.setLoanApplicationId(applicationId);
        ra.setWeightedScore(BigDecimal.valueOf(result.weightedScore()).setScale(2, RoundingMode.HALF_UP));
        ra.setFinalScore(result.finalScore());
        ra.setDecision(result.decision().name());
        ra.setCreatedAt(OffsetDateTime.now());

        RiskAssessmentEntity saved = assessmentRepo.save(ra);

        // Persist factor rows
        result.factors().forEach(fs -> {
            RiskFactorScoreEntity fse = new RiskFactorScoreEntity();
            fse.setRiskAssessmentId(saved.getId());
            fse.setFactorName(fs.factorName());
            fse.setFactorScore(fs.score());
            fse.setReason(fs.reason());
            factorRepo.save(fse);
        });

        // Return API response
        return toResponseFromEngineResult(result);
    }

    private AssessmentResponse toResponseFromExisting(RiskAssessmentEntity ra) {
        List<FactorDto> factors = factorRepo.findByRiskAssessmentIdOrderByIdAsc(ra.getId())
                .stream()
                .map(f -> new FactorDto(f.getFactorName(), f.getFactorScore(), f.getReason()))
                .toList();

        List<String> reasons = factors.stream()
                .map(FactorDto::reason)
                .filter(r -> r != null && !r.isBlank())
                .toList();

        return new AssessmentResponse(
            BigDecimal.valueOf(ra.getWeightedScore().doubleValue()),
            ra.getFinalScore(),
            Decision.valueOf(ra.getDecision()),
            factors,
            reasons
        );
    }

    private AssessmentResponse toResponseFromEngineResult(DecisionResult result) {
        List<FactorDto> factors = result.factors().stream()
                .map(fs -> new FactorDto(fs.factorName(), fs.score(), fs.reason()))
                .toList();

        return new AssessmentResponse(
                BigDecimal.valueOf(result.weightedScore()),
                result.finalScore(),
                result.decision(),
                factors,
                result.reasons()
        );
    }
}
