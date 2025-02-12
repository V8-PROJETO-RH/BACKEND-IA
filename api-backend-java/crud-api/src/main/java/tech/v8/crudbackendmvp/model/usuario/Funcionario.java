package tech.v8.crudbackendmvp.model.usuario;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tech.v8.crudbackendmvp.model.dto.usuario.funcionario.FuncionarioFrontCriacao;
import tech.v8.crudbackendmvp.model.vaga.Vaga;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "funcionario")
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "funcionario_seq")
    @SequenceGenerator(name = "funcionario_seq", sequenceName = "funcionario_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @MapsId
    @OneToOne(mappedBy = "funcionario")
    @EqualsAndHashCode.Exclude
    private Pessoa pessoa;

    @OneToMany(mappedBy = "responsavel", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Vaga> vagas;

    @NotNull
    @Column(name = "departamento", nullable = false, length = 100)
    private String departamento;

    @NotNull
    @Column(name = "funcao", nullable = false, length = 100)
    private String funcao;


    public Funcionario(FuncionarioFrontCriacao dto, Pessoa pessoa) {

        this.pessoa = pessoa;
        this.vagas = new ArrayList<>();

        this.departamento = dto.getDepartamento();
        this.funcao = dto.getFuncao();

    }
}
