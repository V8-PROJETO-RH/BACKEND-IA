package com.mvp.backend.backendmvp.model.dto.candidato;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mvp.backend.backendmvp.model.Candidato;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class CandidatoFrontDTOResposta {
    private long id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private String linkedin_profile;
    private LocalDate data_nascimento;
    private LocalDateTime data_criacao;

    public CandidatoFrontDTOResposta(Candidato candidato) {
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
