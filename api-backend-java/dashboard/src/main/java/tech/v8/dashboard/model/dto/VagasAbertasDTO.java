package tech.v8.dashboard.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record VagasAbertasDTO(
        Integer totalVagasAbertas,
        List<VagaDTO> vagas
) {

public record VagaDTO(
        Integer id,
        String nome,
        String tipoVaga,
        String localidade,
        String modeloVaga,
        @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
        LocalDateTime dataCriacao,
        Integer quantidadeVagas,
        String responsavel
){

}
}
