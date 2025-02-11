package tech.v8.crudbackendmvp.model.dto.usuario.funcionario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.v8.crudbackendmvp.model.usuario.Funcionario;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
public class FuncionarioFrontResumo {
    private long id;

    private String nome;

    private String role;

    public FuncionarioFrontResumo(Funcionario funcionario) {
        this.id = funcionario.getId();

        this.nome = funcionario.getPessoa().getNome();
        this.role = funcionario.getPessoa().getRole().name();
    }
}
