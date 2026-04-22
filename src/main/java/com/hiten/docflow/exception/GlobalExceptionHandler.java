package com.hiten.docflow.exception;

import com.hiten.docflow.dto.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * GlobalExceptionHandler — catches ALL exceptions from ALL controllers.
 *
 * HOW IT WORKS:
 * Without this: Exception → Spring dumps stack trace → client gets 500 + wall of text
 * With this:    Exception → caught here → converted to clean JSON → client gets useful error
 *
 * @RestControllerAdvice = @ControllerAdvice + @ResponseBody
 * Means: "this class handles exceptions globally, and responses are JSON"
 *
 * @ExceptionHandler(SomeException.class) = "when THIS exception is thrown anywhere,
 * run this method instead of the default behavior"
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 — Something wasn't found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(404, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // 409 — Duplicate (username taken, etc.)
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicate(DuplicateResourceException ex) {
        ErrorResponse error = new ErrorResponse(409, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    // 409 — Database constraint violated (e.g. deleting doctor with appointments)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDbConstraint(DataIntegrityViolationException ex) {
        ErrorResponse error = new ErrorResponse(409,
                "Operation not allowed: this record is linked to other data. " +
                "Delete related records first.");
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    // 400 — Bad request (general IllegalArgumentException)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(IllegalArgumentException ex) {
        ErrorResponse error = new ErrorResponse(400, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // 500 — Catch-all for anything else we didn't specifically handle
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
        ErrorResponse error = new ErrorResponse(500, "Something went wrong. Please try again.");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
