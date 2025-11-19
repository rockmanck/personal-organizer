package com.jediorganizer.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * Standardized error response structure for API errors.
 */
@Schema(description = "Error response")
public class ErrorResponse {

    @Schema(description = "Error code", example = "VALIDATION_ERROR")
    private String code;

    @Schema(description = "Error message", example = "Task title is required")
    private String message;

    @Schema(description = "Additional error details")
    private Object details;

    @Schema(description = "Timestamp when error occurred", example = "2025-09-16T10:30:00")
    private LocalDateTime timestamp;

    @Schema(description = "Request path that caused the error", example = "/api/v1/tasks")
    private String path;

    public ErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(String code, String message) {
        this();
        this.code = code;
        this.message = message;
    }

    public ErrorResponse(String code, String message, String path) {
        this(code, message);
        this.path = path;
    }

    public ErrorResponse(String code, String message, Object details, String path) {
        this(code, message, path);
        this.details = details;
    }

    // Getters and setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getDetails() {
        return details;
    }

    public void setDetails(Object details) {
        this.details = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}