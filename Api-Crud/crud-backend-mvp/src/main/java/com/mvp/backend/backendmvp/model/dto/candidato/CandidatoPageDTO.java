package com.mvp.backend.backendmvp.model.dto.candidato;

import java.util.List;

public record CandidatoPageDTO(List<CandidatoFrontDTOResposta> candidatos, int totalPages, long totalElements) {
}
