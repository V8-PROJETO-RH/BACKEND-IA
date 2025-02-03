package tech.v8.crudbackendmvp.model.dto.usuario.candidato.habilidades;

import lombok.Data;
import lombok.NoArgsConstructor;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.CandidatoFrontResumo;
import tech.v8.crudbackendmvp.model.usuario.DetalhesHabilidades;

@Data
@NoArgsConstructor
public class HabilidadeFrontResposta {
    private Long id;
    private CandidatoFrontResumo candidato;
    private String habilidade;

    public HabilidadeFrontResposta(DetalhesHabilidades habilidade) {
        this.id = habilidade.getId();
        this.candidato = new CandidatoFrontResumo(habilidade.getCandidato());
        this.habilidade = habilidade.getHabilidade();
    }
}
