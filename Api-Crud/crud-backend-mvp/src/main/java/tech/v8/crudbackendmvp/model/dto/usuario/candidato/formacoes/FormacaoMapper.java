package tech.v8.crudbackendmvp.model.dto.usuario.candidato.formacoes;

import org.springframework.stereotype.Component;
import tech.v8.crudbackendmvp.model.usuario.Candidato;
import tech.v8.crudbackendmvp.model.usuario.DetalhesFormacao;

@Component
public class FormacaoMapper {
    public static FormacaoFrontResposta toDTO(DetalhesFormacao formacao) {
        if (formacao == null) return null;

        return new FormacaoFrontResposta(formacao);
    }

    public static DetalhesFormacao toFormacao(FormacaoFrontCriacao formacao, Candidato candidato) {
        return new DetalhesFormacao(formacao, candidato);
    }
}
