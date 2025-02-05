package tech.v8.crudbackendmvp.model.dto.prova;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class ProvaFrontCriacao {
    private String descricao;
}
