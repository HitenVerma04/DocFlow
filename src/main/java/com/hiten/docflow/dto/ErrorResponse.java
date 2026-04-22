package com.hiten.docflow.dto;

import java.time.LocalDateTime;

/**
 * ErrorResponse — the standard shape of EVERY error in DocFlow.
 *
 * Instead of: ugly 500-line stack trace
 * Client gets: { "status": 404, "message": "Patient not found", "timestamp": "..." }
 *
 * This is what every professional API returns — a consistent, predictable error format.
 */
public class ErrorResponse {

    private int status;
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    // Getters (Jackson needs these to convert to JSON)
    public int getStatus() { return status; }
    public String getMessage() { return message; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
