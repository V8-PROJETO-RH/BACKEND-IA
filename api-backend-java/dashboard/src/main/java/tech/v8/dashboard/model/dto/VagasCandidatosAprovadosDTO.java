package tech.v8.dashboard.model.dto;

import java.util.List;

public record VagasCandidatosAprovadosDTO(
        Integer totalVagasComAprovados,
        List<VagaDTO> vagas

) {
    public record VagaDTO(
            Integer id,
            String nome,
            List<CandidatoAprovado> candidatosAprovados
    ){}
    public record CandidatoAprovado(
            Integer id,
            String nome,
            Double notaProva,
            Double aderencia
    ){}
}
