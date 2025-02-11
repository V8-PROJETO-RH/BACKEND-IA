package tech.v8.crudbackendmvp.model.dto.vaga;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VagaFrontCriacao {

    @NotBlank(message = "O nome da vaga não pode ser vazio.")
    private String nome;

    @NotBlank(message = "O tipo da vaga deve ser informado.")
    private String tipo; // Campo adicionado

    @NotBlank(message = "O regime de contratação deve ser informado.")
    @JsonProperty("regime_contratacao")
    private String regimeContratacao;

    @NotBlank(message = "A descrição da vaga é obrigatória.")
    private String descricao;

    @NotNull(message = "O modelo da vaga deve ser informado.")
    private String modelo;

    @NotEmpty(message = "A responsabilidade da vaga deve ser informada.")
    private List<String> responsabilidades;


    @Positive(message = "A quantidade de vagas deve ser maior que zero.")
    @NotNull(message = "A quantidade de vagas é obrigatória.")
    private Integer quantidade;

    @NotEmpty(message = "Os requisitos são obrigatórios.")
    private List<String> requisitos;


    @NotNull(message = "O responsável pela vaga deve ser informado.")
    private Long responsavel_id;

    @Positive(message = "A faixa salarial deve ser maior que zero.")
    @NotNull(message = "A faixa salarial é obrigatória.")
    @JsonProperty("faixa_salarial")
    private BigDecimal faixaSalarial;

    @NotNull(message = "O status da vaga deve ser informado.")
    private String status;

    @NotBlank(message = "As atribuições da vaga devem ser informadas.")
    private String atribuicoes;

    @NotEmpty(message = "Os benefícios da vaga devem ser informados.")
    private List<String> beneficios;


    @NotBlank(message = "A localidade da vaga deve ser informada.")
    private String localidade;
}
