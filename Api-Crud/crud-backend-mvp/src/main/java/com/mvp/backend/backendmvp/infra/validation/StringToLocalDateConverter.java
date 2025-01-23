package com.mvp.backend.backendmvp.infra.validation;

import org.springframework.stereotype.Component;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@SuppressWarnings("NullableProblems")
@Component
public class StringToLocalDateConverter implements Converter<String, LocalDate> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public LocalDate convert(String source) {
        try {
            // Tenta converter a string com o formato especificado
            return LocalDate.parse(source, FORMATTER);
        } catch (DateTimeParseException e) {
            // Lança IllegalArgumentException com mensagem específica
            throw new IllegalArgumentException("A data deve estar no formato DD/MM/AAAA.");
        }
    }

}
