package com.mvp.backend.backendmvp.model.dto.funcionario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.mvp.backend.backendmvp.model.Funcionario;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
public class FuncionarioFrontDTOCriacao {

    @NotBlank(message = "O nome do funcionário não pode estar vazio.")
    private String nome;

    @NotBlank(message = "O departamento do funcionário não pode estar vazio.")
    private String departamento;

    @NotBlank(message = "O email do funcionário nao pode estar vazio.")
    private String email;

    @NotBlank(message = "O telefone do funcionário não pode estar vazio.")
    private String telefone;

}
