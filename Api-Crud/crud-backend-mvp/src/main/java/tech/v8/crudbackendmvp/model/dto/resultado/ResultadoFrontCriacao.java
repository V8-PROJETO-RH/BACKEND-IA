package tech.v8.crudbackendmvp.model.dto.resultado;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class ResultadoFrontCriacao {
    @JsonProperty("prova_id")
    private Long provaId;
    @JsonProperty("nota_prova")
    BigDecimal notaProva;
    BigDecimal aderencia;
}
