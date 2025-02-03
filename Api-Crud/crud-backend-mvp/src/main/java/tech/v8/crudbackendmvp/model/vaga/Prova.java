package tech.v8.crudbackendmvp.model.vaga;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import tech.v8.crudbackendmvp.model.dto.prova.ProvaFrontCriacao;

import java.util.ArrayList;
import java.util.List;

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
    @JoinColumn(name = "vaga_id")
    @JsonBackReference
    private Vaga vaga;

    @OneToMany(mappedBy = "prova", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ResultadoFinal> resultados;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotNull
    @Column(name = "estado_logico", nullable = false)
    private Boolean estadoLogico = true;

    public Prova(ProvaFrontCriacao prova, Vaga vaga) {
        this.resultados = new ArrayList<>();

        this.descricao = prova.getDescricao();
        this.vaga = vaga;
    }
}