package com.mvp.backend.backendmvp.model.dto.funcionario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
public class FuncionarioFrontDTOEdicao {

    @NotBlank(message = "O departamento do funcionário não pode estar vazio.")
    private String departamento;

    @NotBlank(message = "O email do funcionário nao pode estar vazio.")
    private String email;

    @NotBlank(message = "O nome do funcionário não pode estar vazio.")
    private String nome;

    @NotBlank(message = "O telefone do funcionário não pode estar vazio.")
    private String telefone;

}
