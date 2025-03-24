package tech.v8.crudbackendmvp.model.dto.vagaaplicada;

import org.springframework.stereotype.Component;
import tech.v8.crudbackendmvp.model.usuario.Candidato;
import tech.v8.crudbackendmvp.model.vaga.Vaga;
import tech.v8.crudbackendmvp.model.vaga.VagaAplicada;

import java.util.List;

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

    public static VagaAplicadaPage toPage(List<VagaAplicada> vagasAplicadas, int page, int size) {
        List<VagaAplicadaFrontResposta> vagas = vagasAplicadas.stream()
                .skip((long) page * size)
                .limit(size)
                .map(VagaAplicadaMapper::toDTO)
                .toList();
        int totalElements = vagasAplicadas.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        return new VagaAplicadaPage(vagas, totalPages, totalElements);
    }
}
