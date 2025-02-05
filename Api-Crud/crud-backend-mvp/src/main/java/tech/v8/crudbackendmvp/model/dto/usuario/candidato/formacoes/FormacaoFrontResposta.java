package tech.v8.crudbackendmvp.model.dto.usuario.candidato.formacoes;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.CandidatoFrontResumo;
import tech.v8.crudbackendmvp.model.usuario.DetalhesFormacao;

import java.time.LocalDate;


@Data
@NoArgsConstructor
public class FormacaoFrontResposta {
    private Long id;
    private CandidatoFrontResumo candidato;
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

    public FormacaoFrontResposta(DetalhesFormacao formacao) {
        this.id = formacao.getId();
        this.candidato = new CandidatoFrontResumo(formacao.getCandidato());

        this.nomeInstituicao = formacao.getNomeInstituicao();
        this.escolaridade = formacao.getEscolaridade();
        this.area = formacao.getArea();
        this.dtInicio = formacao.getDataInicio();
        this.dtFinal = formacao.getDataFinal();
    }
}
