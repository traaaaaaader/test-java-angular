package com.example.masterdetail.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrity(DataIntegrityViolationException ex) {
        return ResponseEntity.badRequest().body(
                Map.of("error", "Unique constraint violation", "message", ex.getMostSpecificCause().getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntime(RuntimeException ex) {
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }
}
