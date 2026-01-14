package com.harshaan.credit_risk_engine.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message){ 
        super(message);
    }
}
