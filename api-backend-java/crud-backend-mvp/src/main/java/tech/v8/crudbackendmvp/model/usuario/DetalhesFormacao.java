package tech.v8.crudbackendmvp.model.usuario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "detalhes_formacao")
public class DetalhesFormacao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "detalhes_habilidades_seq")
    @SequenceGenerator(name = "detalhes_habilidades_seq", sequenceName = "detalhes_habilidades_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidato_id")
    @JsonBackReference
    private Candidato candidato;

    @NotBlank
    @Size(max = 255)
    @Column(name = "nome_instituicao", nullable = false)
    private String nomeInstituicao;

    @NotBlank
    @Size(max = 255)
    @Column(name = "escolaridade", nullable = false)
    private String escolaridade;

    @NotBlank
    @Size(max = 255)
    @Column(name = "area", nullable = false)
    private String area;

    @NotNull
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @NotNull
    @Column(name = "data_final", nullable = false)
    private LocalDate dataFinal;
}
