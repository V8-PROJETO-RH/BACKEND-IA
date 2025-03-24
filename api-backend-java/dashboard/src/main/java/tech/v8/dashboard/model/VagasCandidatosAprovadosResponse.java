package tech.v8.dashboard.model;

import lombok.Data;

import java.util.List;

@Data
public class VagasCandidatosAprovadosResponse {
    private List<VagaCandidatoAprovado> vagasAplicadas;
    private Integer totalPages;
    private Integer totalElements;
}
