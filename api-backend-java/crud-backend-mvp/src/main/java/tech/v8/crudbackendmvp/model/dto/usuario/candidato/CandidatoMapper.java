package tech.v8.crudbackendmvp.model.dto.usuario.candidato;

import org.springframework.stereotype.Component;
import tech.v8.crudbackendmvp.model.usuario.Candidato;
import tech.v8.crudbackendmvp.model.usuario.Pessoa;

@Component
public class CandidatoMapper {
    private CandidatoMapper() {

    }

    public static Candidato toCandidato(CandidatoFrontCriacao dto, Pessoa pessoa) {
        return new Candidato(dto, pessoa);
    }

    public static CandidatoFrontResposta toDTO(Candidato candidato) {
        if (candidato == null) {
            return null;
        }
        return new CandidatoFrontResposta(candidato);
    }


}
