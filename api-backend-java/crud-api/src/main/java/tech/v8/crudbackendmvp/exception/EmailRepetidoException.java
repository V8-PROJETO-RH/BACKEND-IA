package tech.v8.crudbackendmvp.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST)
public class EmailRepetidoException extends RuntimeException {
    public EmailRepetidoException(String message) {
        super(message);
    }
}
