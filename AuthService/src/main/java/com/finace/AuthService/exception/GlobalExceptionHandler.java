package com.finace.AuthService.exception;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.*;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

    private final MessageSource messageSource;
    private final ObjectMapper objectMapper;

    public GlobalExceptionHandler(MessageSource messageSource, ObjectMapper objectMapper) {
        this.messageSource = messageSource;
        this.objectMapper = objectMapper;
    }

    // ---------------------------
    // Common Error Response DTO
    // ---------------------------
    @Data
    @AllArgsConstructor
    static class ErrorResponse {
        private Instant timestamp;
        private int status;
        private String path;
        private String message;
        private Map<String, List<String>> errors;
    }

    private ErrorResponse buildErrorResponse(HttpServletRequest request,
                                             HttpStatus status,
                                             String message,
                                             Map<String, List<String>> errors) {
        return new ErrorResponse(
                Instant.now(),
                status.value(),
                request.getRequestURI(),
                message,
                errors
        );
    }

    private String getPrincipal(HttpServletRequest request) {
        return request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : "Anonymous";
    }

    // ---------------------------
    // Exception Handlers
    // ---------------------------

    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageConversion(HttpMessageConversionException ex, HttpServletRequest request) {
        log.error("User {} triggered {}", getPrincipal(request), ex.getClass().getSimpleName(), ex);
        Map<String, List<String>> errors = Map.of("", List.of(ex.getMostSpecificCause().getMessage()));
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(buildErrorResponse(request, HttpStatus.UNPROCESSABLE_ENTITY, "Invalid request format", errors));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {
        log.error("User {} triggered {}", getPrincipal(request), ex.getClass().getSimpleName(), ex);
        BindingResult bindingResult = ex.getBindingResult();
        Map<String, List<String>> errors = new HashMap<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.computeIfAbsent(fieldError.getField(), k -> new ArrayList<>())
                    .add(messageSource.getMessage(fieldError, LocaleContextHolder.getLocale()));
        }
        for (ObjectError objectError : bindingResult.getGlobalErrors()) {
            errors.computeIfAbsent("", k -> new ArrayList<>())
                    .add(messageSource.getMessage(objectError, LocaleContextHolder.getLocale()));
        }

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(buildErrorResponse(request, HttpStatus.UNPROCESSABLE_ENTITY, "Validation failed", errors));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
        log.error("User {} triggered {}", getPrincipal(request), ex.getClass().getSimpleName(), ex);
        Map<String, List<String>> errors = new HashMap<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.computeIfAbsent(violation.getPropertyPath().toString(), k -> new ArrayList<>())
                    .add(messageSource.getMessage(violation.getMessage(), null, violation.getMessage(), LocaleContextHolder.getLocale()));
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(buildErrorResponse(request, HttpStatus.BAD_REQUEST, "Validation error", errors));
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<ErrorResponse> handleOptimisticLock(ObjectOptimisticLockingFailureException ex, HttpServletRequest request) {
        log.error("User {} triggered {}", getPrincipal(request), ex.getClass().getSimpleName(), ex);
        Map<String, List<String>> errors = Map.of("optimisticLock", List.of("Concurrent update detected. Please retry."));
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(buildErrorResponse(request, HttpStatus.CONFLICT, "Optimistic locking failure", errors));
    }

//    @ExceptionHandler(HttpClientErrorException.class)
//    public ResponseEntity<ErrorResponse> handleHttpClientError(HttpClientErrorException ex, HttpServletRequest request) {
//        log.error("User {} triggered {}", getPrincipal(request), ex.getClass().getSimpleName(), ex);
//        Map<String, List<String>> errors = Map.of("http", List.of(ex.getStatusText()));
//        return ResponseEntity
//                .status(ex.getStatusCode())
//                .body(buildErrorResponse(request, ex.getStatusCode(), "External service error", errors));
//    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrity(DataIntegrityViolationException ex, HttpServletRequest request) {
        log.error("User {} triggered {}", getPrincipal(request), ex.getClass().getSimpleName(), ex);
        Map<String, List<String>> errors = Map.of("database", List.of(ex.getMostSpecificCause().getMessage()));
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(buildErrorResponse(request, HttpStatus.CONFLICT, "Data integrity violation", errors));
    }

//    @ExceptionHandler(ResponseStatusException.class)
//    public ResponseEntity<ErrorResponse> handleResponseStatus(ResponseStatusException ex, HttpServletRequest request) {
//        log.error("User {} triggered {}", getPrincipal(request), ex.getClass().getSimpleName(), ex);
//        Map<String, List<String>> errors = Map.of("status", List.of(ex.getReason()));
//        return ResponseEntity
//                .status(ex.getStatus())
//                .body(buildErrorResponse(request, ex.getStatus(), "Response status exception", errors));
//    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex, HttpServletRequest request) {
        log.error("User {} triggered {}", getPrincipal(request), ex.getClass().getSimpleName(), ex);
        Map<String, List<String>> errors = Map.of("exception", List.of(ex.getMessage() != null ? ex.getMessage() : "Unexpected error"));
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error occurred", errors));
    }


}
