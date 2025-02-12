package tech.v8.crudbackendmvp.exception;

import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST)
public class DataInvalidaException extends RuntimeException {

    public DataInvalidaException(String message) {
        super(message);
    }

}
