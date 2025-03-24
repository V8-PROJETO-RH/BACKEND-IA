package tech.v8.dashboard.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Base exception class for API related exceptions
 */
public abstract class ApiException extends RuntimeException {
    
    private final HttpStatus status;
    private final LocalDateTime timestamp;
    
    protected ApiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }
    
    protected ApiException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }
    
    public HttpStatus getStatus() {
        return status;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}