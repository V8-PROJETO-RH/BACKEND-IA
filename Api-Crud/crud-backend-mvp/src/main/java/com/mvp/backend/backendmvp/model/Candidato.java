package com.mvp.backend.backendmvp.model;

import com.mvp.backend.backendmvp.infra.validation.Telefone;
import com.mvp.backend.backendmvp.model.dto.candidato.CandidatoFrontDTOCriacao;
import com.mvp.backend.backendmvp.model.dto.candidato.CandidatoFrontDTOResposta;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "candidatos")
@SQLDelete(sql = "UPDATE candidatos SET estado_logico = false WHERE id = ?")
public class Candidato {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "candidato_seq")
    @SequenceGenerator(name = "candidato_seq", sequenceName = "candidato_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotBlank
    @Size(max = 255)
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotBlank
    @Email(message = "endereço de email inválido")
    @Size(max = 255)
    @Column(name = "email", nullable = false)
    private String email;

    @NotBlank
    @Size(max = 255)
    @Column(name = "cpf", nullable = false)
    private String cpf;

    @NotNull
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @NotBlank
    @Size(max = 20)
    @Telefone
    @Column(name = "telefone", nullable = false)
    private String telefone;

    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @Size(max = 255)
    @Column(name = "linkedin_profile")
    private String linkedinProfile;

    @OneToMany(mappedBy = "candidato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AderenciaVaga> aderenciasVagas;

    @NotNull
    @Column(name = "estado_logico", nullable = false)
    private Boolean estadoLogico = true;

    public Candidato(CandidatoFrontDTOResposta dto) {
        this.nome = dto.getNome();
        this.email = dto.getEmail();
        this.cpf = dto.getCpf();
        this.dataNascimento = dto.getData_nascimento();
        this.telefone = dto.getTelefone();
        this.dataCriacao = LocalDateTime.now();
        this.linkedinProfile = dto.getLinkedin_profile();
        this.aderenciasVagas = new ArrayList<>();
    }

    public Candidato(CandidatoFrontDTOCriacao dto) {
        this.nome = dto.getNome();
        this.email = dto.getEmail();
        this.cpf = dto.getCpf();
        this.dataNascimento = dto.getData_nascimento();
        this.telefone = dto.getTelefone();
        this.dataCriacao = LocalDateTime.now();
        this.linkedinProfile = dto.getLinkedin_profile();
        this.aderenciasVagas = new ArrayList<>();
    }
}
