package tech.v8.crudbackendmvp.model.dto.usuario.candidato;

import java.util.List;

public record CandidatoPage(List<CandidatoFrontResposta> candidatos, int totalPages, long totalElements) {
}
