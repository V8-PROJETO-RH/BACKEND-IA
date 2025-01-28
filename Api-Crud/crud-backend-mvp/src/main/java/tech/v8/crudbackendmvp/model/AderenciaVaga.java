package tech.v8.crudbackendmvp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "aderenciavagas")
public class AderenciaVaga {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aderencia_vaga_seq")
    @SequenceGenerator(name = "aderencia_vaga_seq", sequenceName = "aderencia_vaga_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_candidato")
    private Candidato candidato;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vaga")
    private Vaga vaga;

    @NotNull
    @Column(name = "percentual_aderencia", precision = 5, scale = 2, nullable = false)
    private BigDecimal percentualAderencia;

    @NotNull
    @Column(name = "data_calculo", nullable = false)
    private LocalDateTime dataCalculo;
}