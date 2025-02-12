package tech.v8.crudbackendmvp.model.dto.usuario.funcionario;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.v8.crudbackendmvp.model.usuario.Funcionario;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
public class FuncionarioFrontResposta {
    private long id;

    private String nome;

//    private List<VagaFrontResposta> vagas;

    private String email;
    private String cpf;
    @JsonProperty("data_nascimento")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;
    private String role;
    private String departamento;
    private String funcao;



    public FuncionarioFrontResposta(Funcionario funcionario) {
        this.id = funcionario.getId();

        this.nome = funcionario.getPessoa().getNome();

//        funcionario.getVagas().forEach(vaga ->  this.vagas.add(new VagaFrontResposta(vaga)));

        this.email = funcionario.getPessoa().getEmail();
        this.cpf = funcionario.getPessoa().getCpf();
        this.dataNascimento = funcionario.getPessoa().getDataNascimento();
        this.role = funcionario.getPessoa().getRole().name();
        this.departamento = funcionario.getDepartamento();
        this.funcao = funcionario.getFuncao();

    }
}
