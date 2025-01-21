package com.mvp.backend.backendmvp.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Classe para estruturar a resposta do erro
    @Getter
    private static class ErrorResponse {
        private final String timestamp;
        private final int status;
        private final String error;
        private final String message;
        private final Map<String, String> fieldErrors;

        public ErrorResponse(HttpStatus status, String message, Map<String, String> fieldErrors) {
            this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy"));
            this.status = status.value();
            this.error = status.getReasonPhrase();
            this.message = message;
            this.fieldErrors = fieldErrors;
        }

    }

    // Trata erros de validação (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();

        // Captura cada mensagem de erro associada ao campo inválido
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }

        // Cria a resposta no formato padronizado
        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Erro de validação nos campos fornecidos.",
                fieldErrors
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Trata ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                null // Sem erros de campo específicos
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Trata ConstraintViolationException
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> fieldErrors = new HashMap<>();

        // Itera sobre as violações e adiciona as mensagens ao mapa
        ex.getConstraintViolations().forEach(violation -> {
            String field = violation.getPropertyPath().toString(); // Propriedade inválida
            String message = violation.getMessage(); // Mensagem de erro
            fieldErrors.put(field, message);
        });

        // Cria a resposta no formato padronizado
        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Erro de validação de restrição nos parâmetros fornecidos.",
                fieldErrors
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}