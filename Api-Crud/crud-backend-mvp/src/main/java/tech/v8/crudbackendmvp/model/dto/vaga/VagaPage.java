package tech.v8.crudbackendmvp.model.dto.vaga;

import java.util.List;

public record VagaPage(List<VagaFrontResposta> vagas, int totalPages, long totalElements) {
}
