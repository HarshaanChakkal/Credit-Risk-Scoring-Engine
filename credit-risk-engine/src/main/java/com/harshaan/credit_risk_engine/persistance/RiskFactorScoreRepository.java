package com.harshaan.credit_risk_engine.persistance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface RiskFactorScoreRepository extends JpaRepository<RiskFactorScoreEntity, Long>{
    List<RiskFactorScoreEntity> findByRiskAssessmentIdOrderByIdAsc(Long riskAssessmentid);
}
