package tech.v8.crudbackendmvp.model.dto.candidato;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class CandidatoFrontCriacao {

    @NotBlank(message = "O nome do candidato não pode estar vazio.")
    private String nome;

    @NotBlank(message = "O CPF do candidato não pode estar vazio.")
    @Pattern(
            regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}",
            message = "O CPF deve estar no formato XXX.XXX.XX-XX."
    )
    private String cpf;

    @NotBlank(message = "o email do candidato não pode estar vazio.")
    private String email;

    @NotBlank(message = "o linkedin do candidato não pode estar vazio.")
    private String linkedin_profile;

    @NotBlank(message = "o telefone do candidato não pode estar vazio.")
    private String telefone;

    @NotNull(message = "A data de nascimento do candidato não pode ser nula.")
    @JsonFormat(pattern = "dd/MM/yyyy") // Define o formato esperado no JSON
    private LocalDate data_nascimento;


}
