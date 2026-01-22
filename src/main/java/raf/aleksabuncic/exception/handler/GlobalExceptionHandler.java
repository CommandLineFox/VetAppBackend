package raf.aleksabuncic.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import raf.aleksabuncic.exception.CustomException;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        log.warn("Handling custom exception: {}", ex.getMessage());
        return buildResponse(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));

        log.warn("Validation error: {}", message);
        return buildResponse(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrity(DataIntegrityViolationException ex) {
        log.warn("Database constraint violation: {}", ex.getMessage());
        return buildResponse(HttpStatus.CONFLICT, "Database constraint violation");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception ex) {
        log.error("Unexpected error: {}", ex.getMessage());
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {
        return buildResponse(HttpStatus.FORBIDDEN, "Access denied");
    }

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(new ErrorResponse(status.value(), message, Instant.now()));
    }
}
