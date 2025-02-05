package tech.v8.crudbackendmvp.model.dto.prova;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.v8.crudbackendmvp.model.vaga.Prova;


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class ProvaFrontResumo {
    private Long id;
    private String descricao;

    public ProvaFrontResumo(Prova prova) {
        this.id = prova.getId();
        this.descricao = prova.getDescricao();
    }
}
