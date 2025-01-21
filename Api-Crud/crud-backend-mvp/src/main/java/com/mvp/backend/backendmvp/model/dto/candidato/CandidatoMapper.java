package com.mvp.backend.backendmvp.model.dto.candidato;

import com.mvp.backend.backendmvp.model.Candidato;
import org.springframework.stereotype.Component;

@Component
public class CandidatoMapper {
    private CandidatoMapper() {

    }

    public static Candidato toCandidato(CandidatoFrontDTOResposta dto) {return new Candidato(dto);}
    public static Candidato toCandidato(CandidatoFrontDTOCriacao dto) {return new Candidato(dto);}

    public static CandidatoFrontDTOResposta toDTO(Candidato candidato) {
        if (candidato == null) {
            return null;
        }
        return new CandidatoFrontDTOResposta(candidato);
    }


}
