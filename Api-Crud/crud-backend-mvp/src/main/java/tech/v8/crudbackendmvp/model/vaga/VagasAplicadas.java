package tech.v8.crudbackendmvp.model.vaga;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.v8.crudbackendmvp.model.usuario.Candidato;

@Data
@NoArgsConstructor
@Entity
@Table(name = "vagas_aplicadas")
public class VagasAplicadas {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vagas_aplicadas_seq")
    @SequenceGenerator(name = "vagas_aplicadas_seq", sequenceName = "vagas_aplicadas_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_candidato")
    private Candidato candidato;

    @ManyToOne
    @JoinColumn(name = "id_vaga")
    private Vaga vaga;

    @ManyToOne
    @JoinColumn(name = "id_resultado_final")
    private ResultadoFinal resultadoFinal;

    @NotNull
    @Column(name = "nome_indicacao", nullable = false)
    private String nomeIndicacao;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;
}