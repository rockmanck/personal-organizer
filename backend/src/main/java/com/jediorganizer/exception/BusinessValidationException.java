package com.jediorganizer.exception;

/**
 * Custom exception for business rule validation errors.
 */
public class BusinessValidationException extends RuntimeException {
    private final String errorCode;

    public BusinessValidationException(String message) {
        super(message);
        this.errorCode = "BUSINESS_VALIDATION_ERROR";
    }

    public BusinessValidationException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessValidationException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "BUSINESS_VALIDATION_ERROR";
    }

    public BusinessValidationException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}