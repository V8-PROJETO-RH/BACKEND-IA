package tech.v8.crudbackendmvp.model.dto.usuario.candidato;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.v8.crudbackendmvp.model.usuario.Candidato;


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class CandidatoFrontResumo {
    private long id;

    private String nome;

    private String email;
    private String cpf;
    private String role;


    public CandidatoFrontResumo(Candidato candidato) {
        this.id = candidato.getId();
        this.nome = candidato.getPessoa().getNome();
        this.email = candidato.getPessoa().getEmail();
        this.cpf = candidato.getPessoa().getCpf();
        this.role = candidato.getPessoa().getRole().name();

    }
}
