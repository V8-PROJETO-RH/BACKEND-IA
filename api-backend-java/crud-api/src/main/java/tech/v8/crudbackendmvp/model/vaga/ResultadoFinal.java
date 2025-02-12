package tech.v8.crudbackendmvp.model.vaga;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import tech.v8.crudbackendmvp.model.dto.resultado.ResultadoFrontCriacao;
import tech.v8.crudbackendmvp.model.usuario.Candidato;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "resultado_final")
@SQLDelete(sql = "UPDATE resultado_final SET estado_logico = false WHERE id = ?")
public class ResultadoFinal {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resultado_final_seq")
    @SequenceGenerator(name = "resultado_final_seq", sequenceName = "resultado_final_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prova_id", nullable = false)
    @JsonBackReference
    private Prova prova;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidato_id", nullable = false)
    private Candidato candidato;

    @OneToMany(mappedBy = "resultadoFinal", cascade = CascadeType.ALL)
    private List<VagaAplicada> vagasAplicadas;

    @Column(name = "nota",  precision = 5, scale = 2, nullable = false)
    private BigDecimal nota;

    @Column(name = "aderencia",  precision = 5, scale = 2, nullable = false)
    private BigDecimal aderencia;

    public ResultadoFinal(ResultadoFrontCriacao dto, VagaAplicada vagaAplicada, Prova prova) {
        this.prova = prova;
        this.candidato = vagaAplicada.getCandidato();
        this.vagasAplicadas = new ArrayList<>();
        this.vagasAplicadas.add(vagaAplicada);
        this.nota = dto.getNotaProva();
        this.aderencia = dto.getAderencia();
    }
}
