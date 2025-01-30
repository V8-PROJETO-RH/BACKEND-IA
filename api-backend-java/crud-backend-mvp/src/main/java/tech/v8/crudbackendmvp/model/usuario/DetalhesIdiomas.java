package tech.v8.crudbackendmvp.model.usuario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "detalhes_idiomas")
public class DetalhesIdiomas {
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
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotBlank
    @Size(max = 255)
    @Column(name = "proficiencia", nullable = false)
    private String proficiencia;
}
