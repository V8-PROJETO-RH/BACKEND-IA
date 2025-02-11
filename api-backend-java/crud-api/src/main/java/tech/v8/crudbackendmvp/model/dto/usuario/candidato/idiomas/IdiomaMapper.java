package tech.v8.crudbackendmvp.model.dto.usuario.candidato.idiomas;

import org.springframework.stereotype.Component;
import tech.v8.crudbackendmvp.model.usuario.Candidato;
import tech.v8.crudbackendmvp.model.usuario.DetalhesIdiomas;

@Component
public class IdiomaMapper {
    public static IdiomaFrontResposta toDTO(DetalhesIdiomas idioma) {
        if (idioma == null) return null;

        return new IdiomaFrontResposta(idioma);
    }

    public static DetalhesIdiomas toIdioma(IdiomaFrontCriacao idioma, Candidato candidato) {
        return new DetalhesIdiomas(idioma, candidato);
    }
}
