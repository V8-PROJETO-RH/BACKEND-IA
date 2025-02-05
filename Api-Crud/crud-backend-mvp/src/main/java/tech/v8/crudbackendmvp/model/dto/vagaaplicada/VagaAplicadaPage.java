package tech.v8.crudbackendmvp.model.dto.vagaaplicada;


import java.util.List;

public record VagaAplicadaPage(List<VagaAplicadaFrontResposta> vagasAplicadas, int totalPages, long totalElements) {
}
