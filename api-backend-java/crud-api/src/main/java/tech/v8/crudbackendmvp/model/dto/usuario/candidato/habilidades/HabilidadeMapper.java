package tech.v8.crudbackendmvp.model.dto.usuario.candidato.habilidades;

import org.springframework.stereotype.Component;
import tech.v8.crudbackendmvp.model.usuario.Candidato;
import tech.v8.crudbackendmvp.model.usuario.DetalhesHabilidades;

@Component
public class HabilidadeMapper {
    public static HabilidadeFrontResposta toDTO(DetalhesHabilidades habilidade) {
        if (habilidade == null) return null;

        return new HabilidadeFrontResposta(habilidade);
    }

    public static DetalhesHabilidades toHabilidade(HabilidadeFrontCriacao habilidade, Candidato candidato) {
        return new DetalhesHabilidades(habilidade, candidato);
    }
}
