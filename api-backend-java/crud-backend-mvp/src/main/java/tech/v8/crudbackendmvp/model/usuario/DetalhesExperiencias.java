package tech.v8.crudbackendmvp.model.usuario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.v8.crudbackendmvp.model.enums.Modelo;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "detalhes_experiencias")
public class DetalhesExperiencias {
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
    @Column(name = "titulo", nullable = false)
    private String titulo;

    @NotBlank
    @Size(max = 255)
    @Column(name = "empresa", nullable = false)
    private String empresa;

    @NotBlank
    @Size(max = 255)
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotBlank
    @Size(max = 255)
    @Column(name = "localidade", nullable = false)
    private String localidade;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "modelo", nullable = false, length = 45)
    private Modelo modelo;


    @NotBlank
    @Size(max = 255)
    @Column(name = "competencias", nullable = false)
    private String competencias;

    @NotNull
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @NotNull
    @Column(name = "data_final", nullable = false)
    private LocalDate dataFinal;
}
