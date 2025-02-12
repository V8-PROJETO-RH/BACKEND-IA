package tech.v8.crudbackendmvp.model.dto.usuario.funcionario;

import java.util.List;

public record FuncionarioPage(List<FuncionarioFrontResposta> funcionarios, int totalPages, long totalElements) {
}
