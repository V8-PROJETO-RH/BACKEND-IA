package com.mvp.backend.backendmvp.model.dto.candidato;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class CandidatoFrontDTOEdicao {

    @NotBlank(message = "O nome do candidato não pode estar vazio.")
    private String nome;

    @NotBlank(message = "o cpf do candidato não pode estar vazio.")
    private String cpf;

    @NotBlank(message = "o email do candidato não pode estar vazio.")
    private String email;

    @NotBlank(message = "o linkedin do candidato não pode estar vazio.")
    private String linkedin_profile;

    @NotBlank(message = "o telefone do candidato não pode estar vazio.")
    private String telefone;

    @NotNull(message = "A data de nascimento do candidato não pode ser nula.")
    private LocalDate data_nascimento;


}