package tech.v8.crudbackendmvp.model.dto.usuario.pessoa;

import org.springframework.stereotype.Component;
import tech.v8.crudbackendmvp.model.usuario.Pessoa;

@Component
public class PessoaMapper {
    private PessoaMapper() {

    }

    public static Pessoa toPessoa(PessoaFrontCriacao dto) {
        return new Pessoa(dto);
    }
}
