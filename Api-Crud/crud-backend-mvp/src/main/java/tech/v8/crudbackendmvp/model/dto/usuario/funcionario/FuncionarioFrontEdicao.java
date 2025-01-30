package tech.v8.crudbackendmvp.model.dto.usuario.funcionario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tech.v8.crudbackendmvp.model.dto.usuario.pessoa.PessoaFrontEdicao;
import tech.v8.crudbackendmvp.model.vaga.Vaga;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
public class FuncionarioFrontEdicao extends PessoaFrontEdicao {

    private List<Vaga> vagas;

    @NotBlank(message = "O departamento do funcionário não pode estar vazio.")
    private String departamento;

    @NotBlank(message = "A função do funcionário não pode estar vazia.")
    private String funcao;

}
