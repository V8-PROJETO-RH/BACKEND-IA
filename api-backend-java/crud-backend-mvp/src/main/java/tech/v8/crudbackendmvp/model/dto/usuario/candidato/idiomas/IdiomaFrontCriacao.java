package tech.v8.crudbackendmvp.model.dto.usuario.candidato.idiomas;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class IdiomaFrontCriacao {
    private String nome;
    private String proficiencia;
}
