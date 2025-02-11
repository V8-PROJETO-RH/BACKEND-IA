package tech.v8.crudbackendmvp.model.dto.resultado;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.v8.crudbackendmvp.model.vaga.ResultadoFinal;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class ResultadoFrontResumo {
    @JsonProperty("nota_prova")
    private BigDecimal notaProva;
    private BigDecimal aderencia;

    public ResultadoFrontResumo(ResultadoFinal resultado) {
        this.notaProva = resultado.getNota();
        this.aderencia = resultado.getAderencia();

    }
}
