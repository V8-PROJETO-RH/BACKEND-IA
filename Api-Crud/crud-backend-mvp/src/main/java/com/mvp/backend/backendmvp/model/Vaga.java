package com.mvp.backend.backendmvp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mvp.backend.backendmvp.model.dto.vaga.VagaFrontDTOCriacao;
import com.mvp.backend.backendmvp.model.dto.vaga.VagaFrontDTOResposta;
import com.mvp.backend.backendmvp.model.enums.StatusVaga;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@Entity
@Table(name = "vagas")
@SQLDelete(sql = "UPDATE vagas SET estado_logico = false WHERE id = ?")
public class Vaga {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vaga_seq")
    @SequenceGenerator(name = "vaga_seq", sequenceName = "vaga_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funcionarios_id")
    @JsonBackReference
    private Funcionario funcionario;

    @OneToMany(mappedBy = "vaga", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Prova> provas;

    @NotNull
    @Length(min = 4)
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "salario", nullable = false)
    private BigDecimal salario;

    @NotNull
    @Column(name = "quantidade", nullable = false)
    private Integer qtd;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotNull
    @Column(name = "responsavel", nullable = false)
    private String responsavel;

    @NotNull
    @Column(name = "contratacao", nullable = false, length = 45)
    private String contratacao;


    @Enumerated(EnumType.STRING) // Converter para String no banco
    @Column(name = "status", length = 45)
    private StatusVaga status;

    @NotNull
    @Column(name = "requisitos", nullable = false)
    private String requisitos;

    @NotNull
    @Column(name = "modelo", nullable = false, length = 45)
    private String modelo;

    @NotNull
    @Column(name = "beneficios", nullable = false)
    private String beneficios;

    @NotNull
    @Column(name = "atribuicoes", nullable = false)
    private String atribuicoes;

    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @NotNull
    @Column(name = "estado_logico", nullable = false)
    private Boolean estadoLogico = true; // Tipo booleano com valor padrão true

    public Vaga(VagaFrontDTOCriacao dto){
        this.nome = dto.getNome();
        this.salario = dto.getSalario();
        this.qtd = dto.getQuantidade();
        this.descricao = dto.getDescricao();
        this.responsavel = dto.getResponsavel();
        this.contratacao = dto.getContratacao();
        this.status = StatusVaga.valueOf(dto.getStatus());
        this.requisitos = dto.getRequisitos();
        this.modelo = dto.getModelo();
        this.estadoLogico = true;
        this.provas = new ArrayList<>();
        this.funcionario = null;
        this.beneficios = dto.getBeneficios();
        this.atribuicoes = dto.getAtribuicoes();
        this.dataCriacao = LocalDateTime.now();
    }

    public Vaga(VagaFrontDTOResposta dto){
        this.nome = dto.getNome();
        this.salario = dto.getSalario();
        this.qtd = dto.getQuantidade();
        this.descricao = dto.getDescricao();
        this.responsavel = dto.getResponsavel();
        this.contratacao = dto.getContratacao();
        this.status = StatusVaga.valueOf(dto.getStatus());
        this.requisitos = dto.getRequisitos();
        this.modelo = dto.getModelo();
        this.estadoLogico = true;
        this.provas = new ArrayList<>();
        this.funcionario = null;
        this.beneficios = dto.getBeneficios();
        this.atribuicoes = dto.getAtribuicoes();
        this.dataCriacao = LocalDateTime.now();
    }
}
