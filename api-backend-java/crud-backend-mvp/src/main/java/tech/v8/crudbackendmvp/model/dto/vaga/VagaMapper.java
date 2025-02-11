package tech.v8.crudbackendmvp.model.dto.vaga;

import org.springframework.stereotype.Component;
import tech.v8.crudbackendmvp.model.vaga.Vaga;

import java.util.List;

@Component
public class VagaMapper {
    private VagaMapper(){

    }

    public static Vaga toVaga(VagaFrontCriacao dto){
        return new Vaga(dto);
    }


    public static VagaFrontResposta toDTO(Vaga vaga){
        if(vaga == null) return null;
        return new VagaFrontResposta(vaga);
    }

    public static VagaPage toPage(List<Vaga> vagasAtivas, int page, int size) {
        List<VagaFrontResposta> vagasDto = vagasAtivas.stream()
                .skip((long) page * size)
                .limit(size)
                .map(VagaMapper::toDTO)
                .toList();

        int totalElements = vagasAtivas.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        return new VagaPage(vagasDto, totalPages, totalElements);
    }
}
