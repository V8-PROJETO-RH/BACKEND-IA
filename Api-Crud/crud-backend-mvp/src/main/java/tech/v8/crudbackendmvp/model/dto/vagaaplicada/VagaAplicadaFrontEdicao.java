package tech.v8.crudbackendmvp.model.dto.vagaaplicada;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VagaAplicadaFrontEdicao {

    @NotNull(message = "O candidato_id da vaga não pode ser nulo.")
    @JsonProperty("candidato_id")
    private Long candidatoId;

    @NotNull(message = "O vaga_id da vaga não pode ser nulo.")
    @JsonProperty("vaga_id")
    private Long vagaId;

    @NotNull(message = "O resultado_final_id da vaga aplicada não pode ser nulo.")
    @JsonProperty("resultado_final_id")
    private Long resultadoFinalId;

    @NotBlank(message = "O nome de indicação da vaga é obrigatório.")
    @JsonProperty("nome_indicacao")
    private String nomeIndicacao;

    @NotBlank(message = "O status da é obrigatório.")
    private String status;
}
