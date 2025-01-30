package tech.v8.crudbackendmvp.model.vaga;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.validator.constraints.Length;
import tech.v8.crudbackendmvp.model.dto.vaga.VagaFrontCriacao;
import tech.v8.crudbackendmvp.model.enums.Modelo;
import tech.v8.crudbackendmvp.model.enums.StatusVaga;
import tech.v8.crudbackendmvp.model.usuario.Funcionario;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "vaga")
@SQLDelete(sql = "UPDATE vagas SET estado_logico = false WHERE id = ?")
public class Vaga {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vaga_seq")
    @SequenceGenerator(name = "vaga_seq", sequenceName = "vaga_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "responsavel_id")
    @JsonBackReference
    private Funcionario responsavel;

    @OneToMany(mappedBy = "vaga", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Prova> provas;

    @NotNull
    @Length(min = 4)
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "tipo", nullable = false, length = 100)
    private String tipo;

    @NotNull
    @Column(name = "localidade", nullable = false)
    private String localidade;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "modelo", nullable = false, length = 45)
    private Modelo modelo;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;


    @NotNull
    @Column(name = "responsabilidade", nullable = false)
    private String responsabilidade;

    @NotNull
    @Column(name = "requisitos", nullable = false)
    private String requisitos;

    @NotNull
    @Column(name = "faixa_salarial", nullable = false)
    private BigDecimal faixaSalarial;

    @NotNull
    @Column(name = "regime_contratacao", nullable = false, length = 45)
    private String regimeContratacao;

    @NotNull
    @Column(name = "beneficios", nullable = false)
    private String beneficios;

    @Enumerated(EnumType.STRING) // Converter para String no banco
    @Column(name = "status", length = 45)
    private StatusVaga status;

    @NotNull
    @Column(name = "quantidade_vagas", nullable = false)
    private Integer qtdVagas;

    @NotNull
    @Column(name = "atribuicoes", nullable = false)
    private String atribuicoes;

    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @NotNull
    @Column(name = "estado_logico", nullable = false)
    private Boolean estadoLogico = true; // Tipo booleano com valor padrão true

    public Vaga(VagaFrontCriacao dto){
        this.responsavel = null;
        this.provas = new ArrayList<>();

        this.nome = dto.getNome();
        this.tipo = dto.getTipo();
        this.localidade = dto.getLocalidade();
        this.descricao = dto.getDescricao();
        this.responsabilidade = dto.getResponsabilidade();
        this.requisitos = dto.getRequisitos();
        this.faixaSalarial = dto.getFaixaSalarial();
        this.regimeContratacao = dto.getRegimeContratacao();
        this.beneficios = dto.getBeneficios();

        this.setModelo(dto.getModelo());
        this.setStatus(dto.getStatus());

        this.qtdVagas = dto.getQuantidade();
        this.atribuicoes = dto.getAtribuicoes();
        this.dataCriacao = LocalDateTime.now();

        this.estadoLogico = true;
    }

    public void setModelo(String modelo) {
        try {
            this.modelo = Modelo.fromString(modelo);
        } catch (IllegalArgumentException e) {
            throw new ConstraintViolationException(e.getMessage(), null); // Lança exceção personalizada
        }
    }

    public void setStatus(String status) {
        try {
            this.status = StatusVaga.fromString(status);
        } catch (IllegalArgumentException e) {
            throw new ConstraintViolationException(e.getMessage(), null); // Lança exceção personalizada
        }
    }


}
