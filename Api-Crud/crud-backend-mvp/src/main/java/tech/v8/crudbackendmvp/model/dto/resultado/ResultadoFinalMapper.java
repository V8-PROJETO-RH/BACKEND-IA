package tech.v8.crudbackendmvp.model.dto.resultado;

import org.springframework.stereotype.Component;
import tech.v8.crudbackendmvp.model.vaga.Prova;
import tech.v8.crudbackendmvp.model.vaga.ResultadoFinal;
import tech.v8.crudbackendmvp.model.vaga.VagaAplicada;

@Component
public class ResultadoFinalMapper {
    private ResultadoFinalMapper(){

    }

    public static ResultadoFinal toResultadoFinal(ResultadoFrontCriacao dto, VagaAplicada vagaAplicada, Prova prova){
        return new ResultadoFinal(dto, vagaAplicada, prova);
    }

    public static ResultadoFrontResposta toDTO(ResultadoFinal resultado){
        if(resultado == null) return null;
        return new ResultadoFrontResposta(resultado);
    }
}
