package com.mvp.backend.backendmvp.model.dto.funcionario;

import java.util.List;

public record FuncionarioPageDTO(List<FuncionarioFrontDTORespostaPage> funcionarios, int totalPages, long totalElements) {
}
