package com.mvp.backend.backendmvp.model.dto.vaga;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mvp.backend.backendmvp.model.Vaga;
import com.mvp.backend.backendmvp.model.dto.funcionario.FuncionarioFrontDTOResposta;
import com.mvp.backend.backendmvp.model.dto.funcionario.FuncionarioMapper;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class VagaFrontDTOResposta {
    private Long id;
    private String contratacao;
    private String descricao;
    private String modelo;
    private String nome;
    private Integer quantidade;
    private String requisitos;
    private String responsavel;
    private BigDecimal salario;
    private String status;
    private FuncionarioFrontDTOResposta funcionario;
    private String atribuicoes;
    private String beneficios;
    private LocalDateTime dataCriacao;

    public VagaFrontDTOResposta(Vaga vaga) {
        this.id = vaga.getId();
        this.contratacao = vaga.getContratacao();
        this.descricao = vaga.getDescricao();
        this.modelo = vaga.getModelo();
        this.nome = vaga.getNome();
        this.quantidade = vaga.getQtd();
        this.requisitos = vaga.getRequisitos();
        this.responsavel = vaga.getResponsavel();
        this.salario = vaga.getSalario();
        this.status = vaga.getStatus().name();
        this.funcionario = FuncionarioMapper.toDTO(vaga.getFuncionario());
        this.atribuicoes = vaga.getAtribuicoes();
        this.beneficios = vaga.getBeneficios();
        this.dataCriacao = vaga.getDataCriacao();
    }
}
