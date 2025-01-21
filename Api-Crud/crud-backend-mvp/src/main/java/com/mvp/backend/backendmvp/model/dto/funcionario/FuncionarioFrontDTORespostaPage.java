package com.mvp.backend.backendmvp.model.dto.funcionario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.mvp.backend.backendmvp.model.Funcionario;
import com.mvp.backend.backendmvp.model.Vaga;
import com.mvp.backend.backendmvp.model.dto.vaga.VagaFrontDTOResposta;
import com.mvp.backend.backendmvp.model.dto.vaga.VagaMapper;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
public class FuncionarioFrontDTORespostaPage {
    private long id;
    private LocalDateTime dataCriacao;
    private String departamento;
    private String email;
    private String nome;
    private String telefone;

    private List<VagaFrontDTOResposta> vagas = new ArrayList<>();

    public FuncionarioFrontDTORespostaPage(Funcionario funcionario, List<Vaga> vagas) {
        this.id = funcionario.getId();
        this.dataCriacao = funcionario.getDataCriacao();
        this.departamento = funcionario.getDepartamento();
        this.email = funcionario.getEmail();
        this.nome = funcionario.getNome();
        this.telefone = funcionario.getTelefone();
        vagas.forEach(vaga -> {this.vagas.add(VagaMapper.toDTO(vaga));});

    }
}
