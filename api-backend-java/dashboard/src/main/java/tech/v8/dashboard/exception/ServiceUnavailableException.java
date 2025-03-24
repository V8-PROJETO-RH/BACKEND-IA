package tech.v8.dashboard.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when an external service is unavailable
 */
public class ServiceUnavailableException extends ApiException {
    
    public ServiceUnavailableException(String message) {
        super(message, HttpStatus.SERVICE_UNAVAILABLE);
    }
    
    public ServiceUnavailableException(String message, Throwable cause) {
        super(message, cause, HttpStatus.SERVICE_UNAVAILABLE);
    }
}