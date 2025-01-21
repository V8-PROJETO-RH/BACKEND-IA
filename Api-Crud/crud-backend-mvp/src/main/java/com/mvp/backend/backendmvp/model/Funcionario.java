package com.mvp.backend.backendmvp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mvp.backend.backendmvp.model.dto.funcionario.FuncionarioFrontDTOCriacao;
import com.mvp.backend.backendmvp.model.dto.funcionario.FuncionarioFrontDTOResposta;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "funcionarios")
@SQLDelete(sql = "UPDATE funcionarios SET estado_logico = false WHERE id = ?")
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "funcionario_seq")
    @SequenceGenerator(name = "funcionario_seq", sequenceName = "funcionario_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Vaga> vagas;

    @NotNull
    @Length(min = 4)
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Email(message = "endereço de email inválido.")
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "telefone", nullable = false, length = 20)
    private String telefone;

    @NotNull
    @Column(name = "departamento", nullable = false, length = 100)
    private String departamento;

    @NotNull
    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @NotNull
    @Column(name = "estado_logico", nullable = false)
    private Boolean estadoLogico = true;

    public Funcionario(FuncionarioFrontDTOResposta dto) {
        this.nome = dto.getNome();
        this.email = dto.getEmail();
        this.telefone = dto.getTelefone();
        this.departamento = dto.getDepartamento();
        this.dataCriacao = LocalDateTime.now();
        this.estadoLogico = true;
        this.vagas = new ArrayList<>();
    }

    public Funcionario(FuncionarioFrontDTOCriacao dto) {
        this.nome = dto.getNome();
        this.email = dto.getEmail();
        this.telefone = dto.getTelefone();
        this.departamento = dto.getDepartamento();
        this.dataCriacao = LocalDateTime.now();
        this.estadoLogico = true;
        this.vagas = new ArrayList<>();
    }
}