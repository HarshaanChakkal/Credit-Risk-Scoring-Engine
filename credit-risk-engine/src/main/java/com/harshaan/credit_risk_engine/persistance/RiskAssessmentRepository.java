package com.harshaan.credit_risk_engine.persistance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface RiskAssessmentRepository extends JpaRepository<RiskAssessmentEntity, Long> {
    Optional<RiskAssessmentEntity> findTopByLoanApplicationIdOrderByCreatedAtDesc(Long loanApplicationId);
}
