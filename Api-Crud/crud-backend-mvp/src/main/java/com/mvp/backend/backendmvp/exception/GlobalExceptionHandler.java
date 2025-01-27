package com.mvp.backend.backendmvp.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ClassEscapesDefinedScope")
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

        if (ex.getConstraintViolations() != null) {
            // Itera sobre violações de validações padrão
            ex.getConstraintViolations().forEach(violation -> {
                String field = violation.getPropertyPath().toString();
                String message = violation.getMessage();
                fieldErrors.put(field, message);
            });
        } else {
            // Caso seja uma violação manual, adiciona mensagem customizada
            fieldErrors.put("status", ex.getMessage());
        }

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Erro de validação nos parâmetros fornecidos.",
                fieldErrors
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Trata a exceção do Status da vaga.

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<GlobalExceptionHandler.ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        GlobalExceptionHandler.ErrorResponse response = new GlobalExceptionHandler.ErrorResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                null // Erros de campo não se aplicam aqui
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Trata erro de datas inválidas (HttpMessageNotReadableException)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();
        Map<String, String> fieldErrors = new HashMap<>();

        // Caso seja erro relacionado ao formato de data (ex.: "28-13-2023")
        if (cause instanceof DateTimeParseException) {
            fieldErrors.put("data_nascimento", "Formato inválido. Use o formato dd/MM/yyyy. Exemplo: 28/11/2004.");
            ErrorResponse response = new ErrorResponse(
                    HttpStatus.BAD_REQUEST,
                    "Erro de desserialização: um campo contém dados inválidos.",
                    fieldErrors
            );
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // Verifica se a exceção contém informações sobre o campo inválido
        if (ex.getMessage().contains("data_nascimento")) {
            fieldErrors.put("data_nascimento", "Valor inválido. Certifique-se de usar o formato dd/MM/yyyy. Exemplo: 28/11/2004.");
        } else if (ex.getMessage().contains("BigDecimal")) {
            fieldErrors.put("salario", "Valor inválido. Certifique-se de inserir um valor numérico.");
        } else {
            fieldErrors.put("cause", "Erro desconhecido na desserialização. Verifique os valores fornecidos." + ex.getMessage());
        }

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Erro de desserialização: um ou mais campos contêm dados inválidos.",
                fieldErrors
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }




}