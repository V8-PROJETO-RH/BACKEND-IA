package com.mvp.backend.backendmvp.model.dto.vaga;

import com.mvp.backend.backendmvp.model.Vaga;
import org.springframework.stereotype.Component;

@Component
public class VagaMapper {
    private VagaMapper(){

    }

    public static Vaga toVaga(VagaFrontDTOCriacao dto){
        return new Vaga(dto);
    }

    public static Vaga toVaga(VagaFrontDTOResposta dto){
        return new Vaga(dto);
    }

    public static VagaFrontDTOResposta toDTO(Vaga vaga){
        if(vaga == null) return null;
        return new VagaFrontDTOResposta(vaga);
    }
}
