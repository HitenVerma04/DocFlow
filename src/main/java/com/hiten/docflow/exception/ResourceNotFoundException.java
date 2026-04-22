package com.hiten.docflow.exception;

/**
 * Thrown when a requested resource doesn't exist.
 * Examples:
 *   - GET /api/patients/999  (patient 999 doesn't exist)
 *   - GET /api/doctors/50    (doctor 50 doesn't exist)
 *
 * Will be caught by GlobalExceptionHandler → returns 404 Not Found
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
