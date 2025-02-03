package tech.v8.crudbackendmvp.model.dto.usuario.funcionario;

import org.springframework.stereotype.Component;
import tech.v8.crudbackendmvp.model.usuario.Funcionario;
import tech.v8.crudbackendmvp.model.usuario.Pessoa;

@Component
public class FuncionarioMapper {
    private FuncionarioMapper() {

    }

    public static Funcionario toFuncionario(FuncionarioFrontCriacao dto, Pessoa pessoa) {
        return new Funcionario(dto, pessoa);
    }


    public static FuncionarioFrontResposta toDTO(Funcionario funcionario) {
        if (funcionario == null) {
            return null;
        }
        return new FuncionarioFrontResposta(funcionario);
    }

}