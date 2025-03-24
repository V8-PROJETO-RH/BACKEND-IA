package tech.v8.dashboard.client;

import tech.v8.dashboard.model.VagasResponse;
import tech.v8.dashboard.model.VagasCandidatosAprovadosResponse;

public interface VagaClient {

    VagasResponse obterVagasPorStatus(String status);
    VagasCandidatosAprovadosResponse obterVagasCandidatosAprovados();


}
