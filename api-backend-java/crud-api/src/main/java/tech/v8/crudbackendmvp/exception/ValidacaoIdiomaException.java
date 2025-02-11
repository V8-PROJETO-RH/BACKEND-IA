package tech.v8.crudbackendmvp.exception;

import java.util.Map;

public class ValidacaoIdiomaException extends RuntimeException {
    private final Map<String, String> fieldErrors;

    public ValidacaoIdiomaException(String message, Map<String, String> fieldErrors) {
        super(message);
        this.fieldErrors = fieldErrors;
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }
}
