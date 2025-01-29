package tech.v8.crudbackendmvp.model.dto.candidato;

import org.springframework.stereotype.Component;
import tech.v8.crudbackendmvp.model.Candidato;

@Component
public class CandidatoMapper {
    private CandidatoMapper() {

    }

    public static Candidato toCandidato(CandidatoFrontResposta dto) {return new Candidato(dto);}
    public static Candidato toCandidato(CandidatoFrontCriacao dto) {return new Candidato(dto);}

    public static CandidatoFrontResposta toDTO(Candidato candidato) {
        if (candidato == null) {
            return null;
        }
        return new CandidatoFrontResposta(candidato);
    }


}
