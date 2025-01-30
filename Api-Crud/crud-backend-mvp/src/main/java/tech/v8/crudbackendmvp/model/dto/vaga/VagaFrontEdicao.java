package tech.v8.crudbackendmvp.model.dto.vaga;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import tech.v8.crudbackendmvp.model.vaga.Prova;

import java.math.BigDecimal;
import java.util.List;

@Data
public class VagaFrontEdicao {

    private Long responsavel_id;

    private List<Prova> provas;

    @NotBlank(message = "O nome da vaga não pode ser vazio.")
    private String nome;

    @NotBlank(message = "O tipo da vaga deve ser informado.")
    private String tipo;

    @NotBlank(message = "A localidade deve ser informada.")
    private String localidade;

    @NotBlank(message = "A descrição da vaga é obrigatória.")
    private String descricao;

    @NotBlank(message = "A responsabilidade da vaga deve ser informada.")
    private String responsabilidade;

    @NotBlank(message = "Os requisitos são obrigatórios.")
    private String requisitos;

    @NotNull(message = "O salário é obrigatório.")
    private BigDecimal faixaSalarial;

    @NotBlank(message = "O regime de contratação deve ser informado.")
    private String regimeContratacao;

    @NotBlank(message = "Os benefícios da vaga devem ser informados.")
    private String beneficios;

    @NotBlank(message = "O modelo da vaga deve ser informado.")
    private String modelo;

    @NotBlank(message = "O status da vaga deve ser informado.")
    private String status;

    @NotNull(message = "A quantidade de vagas é obrigatória.")
    private Integer quantidade;

    @NotBlank(message = "As atribuições da vaga devem ser informadas.")
    private String atribuicoes;

}
