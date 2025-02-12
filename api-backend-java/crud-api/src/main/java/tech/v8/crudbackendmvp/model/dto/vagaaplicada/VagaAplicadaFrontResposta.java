package tech.v8.crudbackendmvp.model.dto.vagaaplicada;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import tech.v8.crudbackendmvp.model.dto.resultado.ResultadoFrontResumo;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.CandidatoFrontResumo;
import tech.v8.crudbackendmvp.model.dto.vaga.VagaFrontResumo;
import tech.v8.crudbackendmvp.model.vaga.VagaAplicada;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VagaAplicadaFrontResposta {
    private Long id;
    private CandidatoFrontResumo candidato;
    private VagaFrontResumo vaga;
    @JsonProperty("resultado_final")
    private ResultadoFrontResumo resultadoFinal;
    private String nomeIndicacao;
    private String status;

    public VagaAplicadaFrontResposta(VagaAplicada vagaAplicada) {
        this.id = vagaAplicada.getId();
        this.candidato = new CandidatoFrontResumo(vagaAplicada.getCandidato());
        this.vaga = new VagaFrontResumo(vagaAplicada.getVaga());
        this.resultadoFinal = new ResultadoFrontResumo(vagaAplicada.getResultadoFinal());

        this.nomeIndicacao = vagaAplicada.getNomeIndicacao();
        this.status = vagaAplicada.getStatus();
    }
}
