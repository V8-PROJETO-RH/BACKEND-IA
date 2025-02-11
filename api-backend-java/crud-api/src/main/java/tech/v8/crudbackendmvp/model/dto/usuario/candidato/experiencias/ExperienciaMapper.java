package tech.v8.crudbackendmvp.model.dto.usuario.candidato.experiencias;

import org.springframework.stereotype.Component;
import tech.v8.crudbackendmvp.model.usuario.Candidato;
import tech.v8.crudbackendmvp.model.usuario.DetalhesExperiencias;

@Component
public class ExperienciaMapper {
    public static ExperienciaFrontResposta toDTO(DetalhesExperiencias experiencia) {
        if (experiencia == null) return null;

        return new ExperienciaFrontResposta(experiencia);
    }

    public static DetalhesExperiencias toExperiencia(ExperienciaFrontCriacao experiencia, Candidato candidato) {
        return new DetalhesExperiencias(experiencia, candidato);
    }
}
