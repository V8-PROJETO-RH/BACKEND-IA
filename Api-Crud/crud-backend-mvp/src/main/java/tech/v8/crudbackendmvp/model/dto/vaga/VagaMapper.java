package tech.v8.crudbackendmvp.model.dto.vaga;

import org.springframework.stereotype.Component;
import tech.v8.crudbackendmvp.model.Vaga;

@Component
public class VagaMapper {
    private VagaMapper(){

    }

    public static Vaga toVaga(VagaFrontCriacao dto){
        return new Vaga(dto);
    }

    public static Vaga toVaga(VagaFrontResposta dto){
        return new Vaga(dto);
    }

    public static VagaFrontResposta toDTO(Vaga vaga){
        if(vaga == null) return null;
        return new VagaFrontResposta(vaga);
    }
}
