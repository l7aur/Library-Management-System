package com.laur.bookshop.config;

import com.laur.bookshop.config.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("Validation error: {}", ex.getMessage()); 
        Map<String, String> errorResponse = new HashMap<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach(error -> {
                    String fieldName = null;
                    if (error instanceof FieldError) {
                        fieldName = ((FieldError) error).getField();
                    } else {
                        fieldName = error.getObjectName();
                    }
                    String errorMessage = error.getDefaultMessage();
                    errorResponse.put(fieldName, errorMessage);
                });

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            AuthorNotFoundException.class, BookNotFoundException.class,
            PublisherNotFoundException.class, AppUserNotFoundException.class
    })
    public ResponseEntity<Map<String, String>> handleNotFoundExceptions(RuntimeException ex) {
        log.error("Entity not found: {}", ex.getMessage());
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            InvalidPassword.class
    })
    public ResponseEntity<Map<String, String>> handleInvalidPasswordExceptions(RuntimeException ex) {
        log.error("Incorrect password: {}", ex.getMessage());
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({
            DuplicateException.class
    })
    public ResponseEntity<Map<String, String>> handleDuplicateExceptions(RuntimeException ex) {
        log.error("Duplicate exception: {}", ex.getMessage());
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({
            Exception.class,
            EmailNotFoundException.class,
            ExpiredSecurityCodeException.class
    })
    public ResponseEntity<Map<String, String>> handleGlobalExceptions(Exception ex) {
        log.error("Unexpected error: {}", ex.getMessage());
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
