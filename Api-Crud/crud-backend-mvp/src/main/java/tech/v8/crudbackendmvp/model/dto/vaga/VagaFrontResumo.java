package tech.v8.crudbackendmvp.model.dto.vaga;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.v8.crudbackendmvp.model.vaga.Vaga;


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class VagaFrontResumo {
    private Long id;
    private String nome;
    private String tipo;

    public VagaFrontResumo(Vaga vaga) {
        this.id = vaga.getId();
        this.nome = vaga.getNome();
        this.tipo = vaga.getTipo();
    }
}
