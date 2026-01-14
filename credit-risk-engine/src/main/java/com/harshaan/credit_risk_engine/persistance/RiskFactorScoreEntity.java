package com.harshaan.credit_risk_engine.persistance;
import jakarta.persistence.*;

@Entity
@Table(name="risk_factor_scores")
public class RiskFactorScoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="risk_assessment_id", nullable=false)
    private Long riskAssessmentId;

    @Column(name="factor_name", nullable=false)
    private String factorName;

    @Column(name="factor_score", nullable=false)
    private int factorScore;

    @Column(name="reason")
    private String reason;

    public RiskFactorScoreEntity() {}

    public Long getId() { return id; }

    public Long getRiskAssessmentId() { return riskAssessmentId; }
    public void setRiskAssessmentId(Long v) { this.riskAssessmentId = v; }

    public String getFactorName() { return factorName; }
    public void setFactorName(String v) { this.factorName = v; }

    public int getFactorScore() { return factorScore; }
    public void setFactorScore(int v) { this.factorScore = v; }

    public String getReason() { return reason; }
    public void setReason(String v) { this.reason = v; }
}
