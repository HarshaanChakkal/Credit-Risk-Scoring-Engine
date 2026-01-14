package com.harshaan.credit_risk_engine.persistance;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.math.BigDecimal;

@Entity
@Table(name="risk_assessments")
public class RiskAssessmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="loan_application_id", nullable=false)
    private Long loanApplicationId;

    @Column(name="weighted_score", nullable=false, precision=5, scale=2)
    private BigDecimal weightedScore;

    @Column(name="final_score", nullable=false)
    private int finalScore;

    @Column(name="decision", nullable=false)
    private String decision;

    @Column(name="created_at", nullable=false)
    private OffsetDateTime createdAt;

    public RiskAssessmentEntity() {}

    public Long getId() { return id; }

    public Long getLoanApplicationId() { return loanApplicationId; }
    public void setLoanApplicationId(Long v) { this.loanApplicationId = v; }

    public BigDecimal getWeightedScore() { return weightedScore; }
    public void setWeightedScore(BigDecimal v) { this.weightedScore = v; }

    public int getFinalScore() { return finalScore; }
    public void setFinalScore(int v) { this.finalScore = v; }

    public String getDecision() { return decision; }
    public void setDecision(String v) { this.decision = v; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime v) { this.createdAt = v; }
}
