package tech.v8.crudbackendmvp.infra.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( { ElementType.FIELD })
@Retention( RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TelefoneValidator.class)
public @interface Telefone {

    // Mensagem de erro padrão
    String message() default "O número de telefone deve estar no formato (XX) XXXXX-XXXX.";

    // Grupos de validação (usado em validações avançadas)
    Class<?>[] groups() default {};

    // Informações adicionais sobre a validação
    Class<? extends Payload>[] payload() default {};
}
