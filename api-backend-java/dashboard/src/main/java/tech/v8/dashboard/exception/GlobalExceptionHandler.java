package tech.v8.dashboard.exception;

import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Object> handleApiException(ApiException ex, WebRequest request) {
        String requestPath = ((ServletWebRequest) request).getRequest().getRequestURI();
        log.error("API Exception: {}", ex.getMessage(), ex);
        
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                ex.getStatus(),
                ex.getMessage(),
                requestPath);
        
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }
    
   
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        
        String requestPath = ((ServletWebRequest) request).getRequest().getRequestURI();
        log.error("Validation error: {}", ex.getMessage(), ex);
        
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
                errors.add(error.getField() + ": " + error.getDefaultMessage()));
        
        ex.getBindingResult().getGlobalErrors().forEach(error ->
                errors.add(error.getObjectName() + ": " + error.getDefaultMessage()));
        
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Validation error",
                requestPath,
                errors);
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {
        
        String requestPath = ((ServletWebRequest) request).getRequest().getRequestURI();
        log.error("Constraint violation: {}", ex.getMessage(), ex);
        
        List<String> errors = new ArrayList<>();
        ex.getConstraintViolations().forEach(violation ->
                errors.add(violation.getPropertyPath() + ": " + violation.getMessage()));
        
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Validation error",
                requestPath,
                errors);
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        
        String requestPath = ((ServletWebRequest) request).getRequest().getRequestURI();
        log.error("Type mismatch: {}", ex.getMessage(), ex);
        
        String error = ex.getName() + " should be of type " + 
                Objects.requireNonNull(ex.getRequiredType()).getSimpleName();
        
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Type mismatch error",
                requestPath,
                List.of(error));
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    

    @ExceptionHandler({HttpClientErrorException.class, RestClientException.class, ResourceAccessException.class})
    public ResponseEntity<Object> handleRestClientException(Exception ex, WebRequest request) {
        String requestPath = ((ServletWebRequest) request).getRequest().getRequestURI();
        log.error("Service communication error: {}", ex.getMessage(), ex);
        
        HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
        String message = "Error communicating with external service";
        
        // If it's specifically an HTTP client error, we can extract more details
        if (ex instanceof HttpClientErrorException httpEx) {
            status = httpEx.getStatusCode().is4xxClientError() 
                    ? HttpStatus.BAD_REQUEST 
                    : HttpStatus.SERVICE_UNAVAILABLE;
            message = httpEx.getStatusText();
        }
        
        // If connection refused or similar
        if (ex.getCause() instanceof ConnectException) {
            message = "External service is unavailable";
        }
        
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                status,
                message,
                requestPath);
        
        return new ResponseEntity<>(errorResponse, status);
    }
    

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllUncaughtException(Exception ex, WebRequest request) {
        String requestPath = ((ServletWebRequest) request).getRequest().getRequestURI();
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred",
                requestPath);
        
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}