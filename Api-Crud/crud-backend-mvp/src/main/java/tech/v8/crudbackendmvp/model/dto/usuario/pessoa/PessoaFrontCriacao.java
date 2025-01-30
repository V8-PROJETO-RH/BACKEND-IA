package tech.v8.crudbackendmvp.model.dto.usuario.pessoa;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
public class PessoaFrontCriacao {

    @NotBlank(message = "O nome não pode estar vazio.")
    private String nome;

    @NotBlank(message = "O email nao pode estar vazio.")
    private String email;

    @NotBlank(message = "O cpf não pode estar vazio.")
    @Pattern(
            regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}",
            message = "O CPF deve estar no formato XXX.XXX.XX-XX."
    )
    private String cpf;

    @JsonProperty("data_nascimento")
    @NotNull(message = "A data de nascimento não pode ser nula.")
    @JsonFormat(pattern = "dd/MM/yyyy") // Define o formato esperado no JSON
    private LocalDate dataNascimento;

    @NotBlank(message = "A senha não pode estar vazia.")
    private String senha;

    @NotBlank(message = "A role não pode estar vazia.")
    private String role;


}
