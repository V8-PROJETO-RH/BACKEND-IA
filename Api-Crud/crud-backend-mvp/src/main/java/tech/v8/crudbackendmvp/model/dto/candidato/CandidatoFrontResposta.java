package tech.v8.crudbackendmvp.model.dto.candidato;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.v8.crudbackendmvp.model.Candidato;

import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class CandidatoFrontResposta {
    private long id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private String linkedin_profile;
    @JsonFormat(pattern = "dd/MM/yyyy") // Formato para serialização (resposta JSON)
    private LocalDate data_nascimento;
    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private LocalDateTime data_criacao;

    public CandidatoFrontResposta(Candidato candidato) {
        this.id = candidato.getId();
        this.nome = candidato.getNome();
        this.cpf = candidato.getCpf();
        this.telefone = candidato.getTelefone();
        this.email = candidato.getEmail();
        this.linkedin_profile = candidato.getLinkedinProfile();
        this.data_criacao = candidato.getDataCriacao();
        this.data_nascimento = candidato.getDataNascimento();
    }
}
