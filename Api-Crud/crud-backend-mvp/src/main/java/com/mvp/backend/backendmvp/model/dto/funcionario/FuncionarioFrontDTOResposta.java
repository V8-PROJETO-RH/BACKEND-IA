package com.mvp.backend.backendmvp.model.dto.funcionario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.mvp.backend.backendmvp.model.Funcionario;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
public class FuncionarioFrontDTOResposta {
    private long id;
    private LocalDateTime dataCriacao;
    private String departamento;
    private String email;
    private String nome;
    private String telefone;

    public FuncionarioFrontDTOResposta(Funcionario funcionario) {
        this.id = funcionario.getId();
        this.dataCriacao = funcionario.getDataCriacao();
        this.departamento = funcionario.getDepartamento();
        this.email = funcionario.getEmail();
        this.nome = funcionario.getNome();
        this.telefone = funcionario.getTelefone();

    }
}
