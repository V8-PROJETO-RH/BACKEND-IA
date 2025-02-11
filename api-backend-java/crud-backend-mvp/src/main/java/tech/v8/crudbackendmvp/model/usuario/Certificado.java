package tech.v8.crudbackendmvp.model.usuario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.certificados.CertificadoFrontCriacao;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "certificado")
public class Certificado {
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

    @NotNull
    @Column(name = "data_emissao", nullable = false)
    private LocalDate dataEmissao;

    @NotNull
    @Column(name = "data_vencimento", nullable = false)
    private LocalDate dataVencimento;

    public Certificado(CertificadoFrontCriacao dto, Candidato candidato) {
        this.candidato = candidato;

        this.nome = dto.getNome();
        this.dataEmissao = dto.getDtEmissao();
        this.dataVencimento = dto.getDtVencimento();
    }
}
