package com.mvp.backend.backendmvp.model.dto.vaga;

import java.util.List;

public record VagaPageDTO(List<VagaFrontDTOResposta> vagas, int totalPages, long totalElements) {
}
