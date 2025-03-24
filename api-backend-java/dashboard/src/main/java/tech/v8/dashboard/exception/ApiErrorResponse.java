package tech.v8.dashboard.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Standardized error response for API errors
 */
@Getter
public class ApiErrorResponse {
    private HttpStatus status;
    private int statusCode;
    private String message;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    private String path;
    private List<String> errors = new ArrayList<>();

    private ApiErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiErrorResponse(HttpStatus status, String message, String path) {
        this();
        this.status = status;
        this.statusCode = status.value();
        this.message = message;
        this.path = path;
    }

    public ApiErrorResponse(HttpStatus status, String message, String path, List<String> errors) {
        this(status, message, path);
        this.errors = errors;
    }

    public void addValidationError(String error) {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        errors.add(error);
    }
}