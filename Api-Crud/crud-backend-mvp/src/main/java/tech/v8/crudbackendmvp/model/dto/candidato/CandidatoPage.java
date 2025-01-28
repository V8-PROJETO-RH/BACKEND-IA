package tech.v8.crudbackendmvp.model.dto.candidato;

import java.util.List;

public record CandidatoPage(List<CandidatoFrontResposta> candidatos, int totalPages, long totalElements) {
}
