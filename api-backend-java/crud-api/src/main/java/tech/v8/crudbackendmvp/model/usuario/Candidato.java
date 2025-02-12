package tech.v8.crudbackendmvp.model.usuario;

import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tech.v8.crudbackendmvp.infra.validation.Telefone;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.CandidatoFrontCriacao;
import tech.v8.crudbackendmvp.model.enums.Genero;
import tech.v8.crudbackendmvp.model.vaga.ResultadoFinal;
import tech.v8.crudbackendmvp.model.vaga.VagaAplicada;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "candidatos")
public class Candidato {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "candidato_seq")
    @SequenceGenerator(name = "candidato_seq", sequenceName = "candidato_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @MapsId
    @OneToOne(mappedBy = "candidato")
    @EqualsAndHashCode.Exclude
    private Pessoa pessoa;

    @OneToMany(mappedBy = "candidato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VagaAplicada> vagasAplicadas;

    @OneToMany(mappedBy = "candidato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResultadoFinal> resultadoProvas;

    @OneToOne(mappedBy = "candidato", cascade = CascadeType.ALL, orphanRemoval = true)
    private DadosAdicionais dadosAdicionais;

    @OneToMany(mappedBy = "candidato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalhesHabilidades> detalhesHabilidades;

    @OneToMany(mappedBy = "candidato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalhesFormacao> detalhesFormacao;

    @OneToMany(mappedBy = "candidato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalhesIdiomas> detalhesIdiomas;

    @OneToMany(mappedBy = "candidato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalhesExperiencias> detalhesExperiencia;

    @OneToMany(mappedBy = "candidato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Certificado> certificados;

    @NotBlank
    @Size(max = 255)
    @Column(name = "nome_completo", nullable = false)
    private String nomeCompleto;

    @NotBlank
    @Size(max = 20)
    @Telefone
    @Column(name = "telefone", nullable = false)
    private String telefone;

    @Size(max = 255)
    @Column(name = "perfil_linkedin")
    private String perfilLinkedin;

    @NotNull
    @Enumerated(EnumType.STRING) // Converter para String no banco
    @Column(name = "genero", length = 45)
    private Genero genero;

    @NotBlank
    @Column(name = "endereco", nullable = false)
    private String endereco;

    @NotBlank
    @Column(name = "numero", nullable = false)
    private String numero;

    @NotBlank
    @Column(name = "complemento", nullable = false)
    private String complemento;

    @NotBlank
    @Column(name = "bairro", nullable = false)
    private String bairro;

    @NotBlank
    @Column(name = "cidade", nullable = false)
    private String cidade;

    @NotBlank
    @Column(name = "estado", nullable = false)
    private String estado;

    public Candidato(CandidatoFrontCriacao dto, Pessoa pessoa) {
        this.vagasAplicadas = new ArrayList<>();
        this.resultadoProvas = new ArrayList<>();
        this.detalhesHabilidades = new ArrayList<>();
        this.detalhesFormacao = new ArrayList<>();
        this.detalhesIdiomas = new ArrayList<>();
        this.detalhesExperiencia = new ArrayList<>();
        this.certificados = new ArrayList<>();
        this.pessoa = pessoa;

        this.nomeCompleto = dto.getNomeCompleto();
        this.telefone = dto.getTelefone();
        this.perfilLinkedin = dto.getPerfilLinkedin();
        this.setGenero(dto.getGenero());
        this.endereco = dto.getEndereco();
        this.numero = dto.getNumero();
        this.complemento = dto.getComplemento();
        this.bairro = dto.getBairro();
        this.cidade = dto.getCidade();
        this.estado = dto.getEstado();
    }

    public void setGenero(String genero) {
        try {
            this.genero = Genero.fromString(genero);
        } catch (IllegalArgumentException e) {
            throw new ConstraintViolationException(e.getMessage(), null); // Lança exceção personalizada
        }
    }
}
