package tech.v8.crudbackendmvp.model.dto.vaga;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VagaFrontCriacao {

    @NotBlank(message = "O nome da vaga não pode ser vazio.")
    private String nome;

    @NotBlank(message = "O tipo da vaga deve ser informado.")
    private String tipo; // Campo adicionado

    @NotBlank(message = "O regime de contratação deve ser informado.")
    private String regimeContratacao;

    @NotBlank(message = "A descrição da vaga é obrigatória.")
    private String descricao;

    @NotNull(message = "O modelo da vaga deve ser informado.")
    private String modelo;

    @NotBlank(message = "A responsabilidade da vaga deve ser informada.")
    private String responsabilidade;

    @Positive(message = "A quantidade de vagas deve ser maior que zero.")
    @NotNull(message = "A quantidade de vagas é obrigatória.")
    private Integer quantidade;

    @NotBlank(message = "Os requisitos são obrigatórios.")
    private String requisitos;

    @NotNull(message = "O responsável pela vaga deve ser informado.")
    private Long responsavel_id;

    @Positive(message = "A faixa salarial deve ser maior que zero.")
    @NotNull(message = "A faixa salarial é obrigatória.")
    private BigDecimal faixaSalarial;

    @NotNull(message = "O status da vaga deve ser informado.")
    private String status;

    @NotBlank(message = "As atribuições da vaga devem ser informadas.")
    private String atribuicoes;

    @NotBlank(message = "Os benefícios da vaga devem ser informados.")
    private String beneficios;

    @NotBlank(message = "A localidade da vaga deve ser informada.")
    private String localidade;
}
