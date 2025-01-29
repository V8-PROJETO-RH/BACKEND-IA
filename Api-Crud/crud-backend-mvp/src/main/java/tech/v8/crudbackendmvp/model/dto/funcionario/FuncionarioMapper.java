package tech.v8.crudbackendmvp.model.dto.funcionario;

import org.springframework.stereotype.Component;
import tech.v8.crudbackendmvp.model.Funcionario;

@Component
public class FuncionarioMapper {
    private FuncionarioMapper() {

    }

    public static Funcionario toFuncionario(FuncionarioFrontResposta dto) {return new Funcionario(dto);}
    public static Funcionario toFuncionario(FuncionarioFrontCriacao dto) {return new Funcionario(dto);}


    public static FuncionarioFrontResposta toDTO(Funcionario funcionario) {
        if (funcionario == null) {
            return null;
        }
        return new FuncionarioFrontResposta(funcionario);
    }

    public static FuncionarioFrontRespostaPage toDTOPage(Funcionario funcionario) {
        if (funcionario == null) {
            return null;
        }
        return new FuncionarioFrontRespostaPage(funcionario, funcionario.getVagas());
    }
}