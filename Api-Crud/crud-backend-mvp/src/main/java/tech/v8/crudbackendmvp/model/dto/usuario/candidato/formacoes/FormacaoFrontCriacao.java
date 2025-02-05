package tech.v8.crudbackendmvp.model.dto.usuario.candidato.formacoes;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class FormacaoFrontCriacao {
    @JsonProperty("nome_Instituicao")
    private String nomeInstituicao;
    private String escolaridade;
    private String area;
    @JsonProperty("dt_Inicio")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dtInicio;
    @JsonProperty("dt_Final")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dtFinal;
}
