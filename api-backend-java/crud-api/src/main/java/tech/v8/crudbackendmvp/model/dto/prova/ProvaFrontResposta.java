package tech.v8.crudbackendmvp.model.dto.prova;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.v8.crudbackendmvp.model.dto.resultado.ResultadoFrontResumo;
import tech.v8.crudbackendmvp.model.dto.vaga.VagaFrontResumo;
import tech.v8.crudbackendmvp.model.vaga.Prova;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class ProvaFrontResposta {
    private Long id;
    private VagaFrontResumo vaga;
    private List<ResultadoFrontResumo> resultados;
    private String descricao;

    public ProvaFrontResposta(Prova prova) {
        this.id = prova.getId();
        this.vaga = new VagaFrontResumo(prova.getVaga());
        this.resultados = new ArrayList<>();
        prova.getResultados().forEach(resultado -> this.resultados.add(new ResultadoFrontResumo(resultado)));
        this.descricao = prova.getDescricao();
    }
}
