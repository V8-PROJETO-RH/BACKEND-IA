package tech.v8.crudbackendmvp.model.dto.vagaaplicada;

import org.springframework.stereotype.Component;
import tech.v8.crudbackendmvp.model.usuario.Candidato;
import tech.v8.crudbackendmvp.model.vaga.Vaga;
import tech.v8.crudbackendmvp.model.vaga.VagaAplicada;

@Component
public class VagaAplicadaMapper {
    private VagaAplicadaMapper(){

    }

    public static VagaAplicada toVagaAplicada(VagaAplicadaFrontCriacao dto, Candidato candidato, Vaga vaga){
        return new VagaAplicada(dto, candidato, vaga);
    }

    public static VagaAplicadaFrontResposta toDTO(VagaAplicada vagaAplicada){
        if(vagaAplicada == null) return null;
        return new VagaAplicadaFrontResposta(vagaAplicada);
    }
}
