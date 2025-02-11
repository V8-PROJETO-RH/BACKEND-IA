package tech.v8.crudbackendmvp.model.dto.usuario.candidato.experiencias;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.v8.crudbackendmvp.model.usuario.DetalhesExperiencias;

import java.time.LocalDate;


@Data
@NoArgsConstructor
public class ExperienciaFrontResposta {
    private Long id;
    private String titulo;
    private String empresa;
    private String descricao;
    private String localidade;
    private String modelo;
    private String competencias;
    @JsonProperty("dt_Inicio")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dtInicio;
    @JsonProperty("dt_Final")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dtFinal;

    public ExperienciaFrontResposta(DetalhesExperiencias experiencia) {
        this.id = experiencia.getId();

        this.titulo = experiencia.getTitulo();
        this.empresa = experiencia.getEmpresa();
        this.descricao = experiencia.getDescricao();
        this.localidade = experiencia.getLocalidade();
        this.modelo = experiencia.getModelo().name();
        this.competencias = experiencia.getCompetencias();
        this.dtInicio = experiencia.getDataInicio();
        this.dtFinal = experiencia.getDataFinal();

    }
}
