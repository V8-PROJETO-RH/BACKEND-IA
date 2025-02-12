package tech.v8.crudbackendmvp.infra.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TelefoneValidator implements ConstraintValidator<Telefone, String> {

    // Expressão regular para validar o formato do número de telefone
    private static final String TELEFONE_REGEX = "\\(\\d{2}\\) \\d{5}-\\d{4}";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Permite valores nulos, para que apenas valores preenchidos sejam validados
        if (value == null || value.isEmpty()) {
            return true;
        }

        // Valida se o telefone segue o formato desejado
        return value.matches(TELEFONE_REGEX);
    }
}