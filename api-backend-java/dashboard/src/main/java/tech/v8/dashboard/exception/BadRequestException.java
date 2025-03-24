package tech.v8.dashboard.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a client sends an invalid request
 */
public class BadRequestException extends ApiException {
    
    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
    
    public BadRequestException(String message, Throwable cause) {
        super(message, cause, HttpStatus.BAD_REQUEST);
    }
}