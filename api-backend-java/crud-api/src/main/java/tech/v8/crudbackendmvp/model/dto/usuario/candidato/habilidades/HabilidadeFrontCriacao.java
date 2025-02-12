package tech.v8.crudbackendmvp.model.dto.usuario.candidato.habilidades;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class HabilidadeFrontCriacao {
    private String habilidade;
}
