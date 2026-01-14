package com.harshaan.credit_risk_engine.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message){
        super(message);
    }
    
}
