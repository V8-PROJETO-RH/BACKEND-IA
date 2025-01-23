package com.mvp.backend.backendmvp.model.dto.vaga;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class VagaFrontDTOEdicao {

    @NotBlank(message = "O nome da vaga não pode ser vazio.")
    private String nome;

    private Long funcionario_id;

    @NotBlank(message = "O tipo de contratação deve ser informado.")
    private String contratacao;

    @NotBlank(message = "A descrição da vaga é obrigatória.")
    private String descricao;

    @NotBlank(message = "O modelo da vaga deve ser informado.")
    private String modelo;

    @NotNull(message = "A quantidade de vagas é obrigatória.")
    private Integer quantidade;

    @NotBlank(message = "Os requisitos são obrigatórios.")
    private String requisitos;

    @NotBlank(message = "O nome do responsável pela vaga não pode ser vazio.")
    private String responsavel;

    @NotNull(message = "O salário é obrigatório.")
    private BigDecimal salario;

    @NotBlank(message = "O status da vaga deve ser informado.")
    private String status;

    @NotBlank(message = "As atribuições da vaga devem ser informadas.")
    private String atribuicoes;

    @NotBlank(message = "Os benefícios da vaga devem ser informados.")
    private String beneficios;

}
