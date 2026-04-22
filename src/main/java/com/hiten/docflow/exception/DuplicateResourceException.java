package com.hiten.docflow.exception;

/**
 * Thrown when trying to create something that already exists.
 * Examples:
 *   - Registering with a username that's already taken
 *   - Adding a patient with a duplicate email (if we add that rule)
 *
 * Will be caught by GlobalExceptionHandler → returns 409 Conflict
 */
public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}
