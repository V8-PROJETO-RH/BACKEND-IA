package tech.v8.crudbackendmvp.model.dto.funcionario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.v8.crudbackendmvp.model.Funcionario;
import tech.v8.crudbackendmvp.model.Vaga;
import tech.v8.crudbackendmvp.model.dto.vaga.VagaFrontResposta;
import tech.v8.crudbackendmvp.model.dto.vaga.VagaMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
public class FuncionarioFrontRespostaPage {
    private long id;
    private LocalDateTime dataCriacao;
    private String departamento;
    private String email;
    private String nome;
    private String telefone;

    private List<VagaFrontResposta> vagas = new ArrayList<>();

    public FuncionarioFrontRespostaPage(Funcionario funcionario, List<Vaga> vagas) {
        this.id = funcionario.getId();
        this.dataCriacao = funcionario.getDataCriacao();
        this.departamento = funcionario.getDepartamento();
        this.email = funcionario.getEmail();
        this.nome = funcionario.getNome();
        this.telefone = funcionario.getTelefone();
        vagas.forEach(vaga -> {this.vagas.add(VagaMapper.toDTO(vaga));});

    }
}
