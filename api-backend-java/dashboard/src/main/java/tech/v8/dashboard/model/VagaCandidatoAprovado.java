package tech.v8.dashboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class VagaCandidatoAprovado {
   private Integer id;
   private CandidatoAprovado candidato;
   private Vaga vaga;
   @JsonProperty("resultado_final")
   private Resultado resultadoFinal;
   private String nomeIndicacao;
   private String status;


   public record CandidatoAprovado(
            Integer id,
            String nome
    ){}
    public record Vaga(
            Integer id,
            String nome,
            String tipo
    ) { }
    public record Resultado(
            @JsonProperty("nota_prova")
            BigDecimal notaProva,
            BigDecimal aderencia
    ){}
}
