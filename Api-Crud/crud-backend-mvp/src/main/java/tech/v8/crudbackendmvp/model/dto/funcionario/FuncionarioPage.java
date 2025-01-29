package tech.v8.crudbackendmvp.model.dto.funcionario;

import java.util.List;

public record FuncionarioPage(List<FuncionarioFrontRespostaPage> funcionarios, int totalPages, long totalElements) {
}
