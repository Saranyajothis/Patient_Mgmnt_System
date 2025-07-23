package com.pm.patientservice.exception;

import jakarta.validation.constraints.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(
                error -> errors.put(error.getObjectName(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex){
        LOGGER.warn("Email Address already exists {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("email", "Email address already in use");
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String, String>> PatientNotFoundException(EmailAlreadyExistsException ex){
        LOGGER.warn("Patient not found {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("email", "Patient not found");
        return ResponseEntity.badRequest().body(errors);
    }


}

