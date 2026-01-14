package com.harshaan.credit_risk_engine.persistance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanApplicationRepository extends JpaRepository<LoanApplicationEntity, Long> {}
