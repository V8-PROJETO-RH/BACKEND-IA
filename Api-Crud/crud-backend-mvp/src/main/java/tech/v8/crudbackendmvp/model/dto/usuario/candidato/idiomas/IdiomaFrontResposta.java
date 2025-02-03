package tech.v8.crudbackendmvp.model.dto.usuario.candidato.idiomas;

import lombok.Data;
import lombok.NoArgsConstructor;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.CandidatoFrontResumo;
import tech.v8.crudbackendmvp.model.usuario.DetalhesIdiomas;


@Data
@NoArgsConstructor
public class IdiomaFrontResposta {
    private Long id;
    private CandidatoFrontResumo candidato;
    private String nome;
    private String proficiencia;

    public IdiomaFrontResposta(DetalhesIdiomas idioma) {
        this.id = idioma.getId();
        this.candidato = new CandidatoFrontResumo(idioma.getCandidato());
        this.nome = idioma.getNome();
        this.proficiencia = idioma.getProficiencia();
    }
}
