package tech.v8.crudbackendmvp.model.dto.prova;

import org.springframework.stereotype.Component;
import tech.v8.crudbackendmvp.model.vaga.Prova;
import tech.v8.crudbackendmvp.model.vaga.Vaga;

@Component
public class ProvaMapper {

    public static Prova toProva(ProvaFrontCriacao prova, Vaga vaga) {
        return new Prova(prova, vaga);
    }

    public static ProvaFrontResposta toDTO(Prova prova) {
        return new ProvaFrontResposta(prova);
    }
}
