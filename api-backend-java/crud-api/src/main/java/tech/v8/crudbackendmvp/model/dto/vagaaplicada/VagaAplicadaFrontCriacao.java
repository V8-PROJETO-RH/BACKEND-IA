package tech.v8.crudbackendmvp.model.dto.vagaaplicada;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VagaAplicadaFrontCriacao {

    @NotNull(message = "O candidato_id da vaga não pode ser nulo.")
    @JsonProperty("candidato_id")
    private Long candidatoId;

    @NotNull(message = "O vaga_id da vaga não pode ser nulo.")
    @JsonProperty("vaga_id")
    private Long vagaId;

    @NotBlank(message = "O nome de indicação da vaga é obrigatório.")
    @JsonProperty("nome_indicacao")
    private String nomeIndicacao;

    @NotBlank(message = "O status da é obrigatório.")
    private String status;
}
