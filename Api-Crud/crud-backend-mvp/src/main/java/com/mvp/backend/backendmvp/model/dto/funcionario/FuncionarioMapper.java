package com.mvp.backend.backendmvp.model.dto.funcionario;

import com.mvp.backend.backendmvp.model.Funcionario;
import org.springframework.stereotype.Component;

@Component
public class FuncionarioMapper {
    private FuncionarioMapper() {

    }

    public static Funcionario toFuncionario(FuncionarioFrontDTOResposta dto) {return new Funcionario(dto);}
    public static Funcionario toFuncionario(FuncionarioFrontDTOCriacao dto) {return new Funcionario(dto);}


    public static FuncionarioFrontDTOResposta toDTO(Funcionario funcionario) {
        if (funcionario == null) {
            return null;
        }
        return new FuncionarioFrontDTOResposta(funcionario);
    }

    public static FuncionarioFrontDTORespostaPage toDTOPage(Funcionario funcionario) {
        if (funcionario == null) {
            return null;
        }
        return new FuncionarioFrontDTORespostaPage(funcionario, funcionario.getVagas());
    }
}
