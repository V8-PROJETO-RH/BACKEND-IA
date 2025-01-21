package com.mvp.backend.backendmvp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "provas")
@SQLDelete(sql = "UPDATE provas SET estado_logico = false WHERE id = ?")
public class Prova {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prova_seq")
    @SequenceGenerator(name = "prova_seq", sequenceName = "prova_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vagas_id")
    @JsonBackReference
    private Vaga vaga;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;


    @NotNull
    @Column(name = "estado_logico", nullable = false)
    private Boolean estadoLogico = true;
}
