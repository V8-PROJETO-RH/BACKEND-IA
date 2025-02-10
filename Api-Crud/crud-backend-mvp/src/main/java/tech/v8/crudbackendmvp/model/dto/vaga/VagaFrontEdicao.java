package tech.v8.crudbackendmvp.model.dto.vaga;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class VagaFrontEdicao {

    private Long responsavel_id;

    @NotBlank(message = "O nome da vaga não pode ser vazio.")
    private String nome;

    @NotBlank(message = "O tipo da vaga deve ser informado.")
    private String tipo;

    @NotBlank(message = "A localidade deve ser informada.")
    private String localidade;

    @NotBlank(message = "A descrição da vaga é obrigatória.")
    private String descricao;

    @NotEmpty(message = "A responsabilidade da vaga deve ser informada.")
    private List<String> responsabilidades;


    @NotEmpty(message = "Os requisitos são obrigatórios.")
    private List<String> requisitos;


    @Positive(message = "A faixa salarial deve ser maior que zero.")
    @NotNull(message = "A faixa salarial é obrigatória.")
    @JsonProperty("faixa_salarial")
    private BigDecimal faixaSalarial;

    @NotBlank(message = "O regime de contratação deve ser informado.")
    @JsonProperty("regime_contratacao")
    private String regimeContratacao;

    @NotEmpty(message = "Os benefícios da vaga devem ser informados.")
    private List<String> beneficios;

    @NotBlank(message = "O modelo da vaga deve ser informado.")
    private String modelo;

    @NotBlank(message = "O status da vaga deve ser informado.")
    private String status;

    @NotNull(message = "A quantidade de vagas é obrigatória.")
    private Integer quantidade;

    @NotBlank(message = "As atribuições da vaga devem ser informadas.")
    private String atribuicoes;

}
