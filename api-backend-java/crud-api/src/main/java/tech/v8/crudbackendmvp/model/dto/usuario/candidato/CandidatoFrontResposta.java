package tech.v8.crudbackendmvp.model.dto.usuario.candidato;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.v8.crudbackendmvp.model.usuario.Candidato;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class CandidatoFrontResposta {
    private long id;

    private String nome;

    private String email;
    private String cpf;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonProperty("data_nascimento")
    private LocalDate dataNascimento;
    private String role;

    @JsonProperty("nome_completo")
    private String nomeCompleto;
    private String telefone;
    @JsonProperty("perfil_linkedin")
    private String perfilLinkedin;
    private String genero;
    private String endereco;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;

    public CandidatoFrontResposta(Candidato candidato) {
        this.id = candidato.getId();
        this.nome = candidato.getPessoa().getNome();
        this.email = candidato.getPessoa().getEmail();
        this.cpf = candidato.getPessoa().getCpf();
        this.dataNascimento = candidato.getPessoa().getDataNascimento();
        this.role = candidato.getPessoa().getRole().name();

        this.nomeCompleto = candidato.getNomeCompleto();
        this.telefone = candidato.getTelefone();
        this.perfilLinkedin = candidato.getPerfilLinkedin();
        this.genero = candidato.getGenero().name();
        this.endereco = candidato.getEndereco();
        this.numero = candidato.getNumero();
        this.complemento = candidato.getComplemento();
        this.bairro = candidato.getBairro();
        this.cidade = candidato.getCidade();
        this.estado = candidato.getEstado();
    }
}
