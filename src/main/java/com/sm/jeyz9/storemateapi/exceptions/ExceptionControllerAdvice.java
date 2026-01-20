package com.sm.jeyz9.storemateapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(value = WebException.class)
    public final ResponseEntity<Map<String, Object>> handleWebException(WebException exception) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", exception.getStatus().value());
        body.put("error", exception.getStatus().getReasonPhrase());
        body.put("message", exception.getMessage());
        body.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(body, exception.getStatus());
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
