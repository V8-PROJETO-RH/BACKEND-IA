package tech.v8.crudbackendmvp.model.usuario;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import tech.v8.crudbackendmvp.model.vaga.Vaga;
import tech.v8.crudbackendmvp.model.dto.usuario.funcionario.FuncionarioFrontCriacao;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "funcionario")
@SQLDelete(sql = "UPDATE funcionarios SET estado_logico = false WHERE id = ?")
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "funcionario_seq")
    @SequenceGenerator(name = "funcionario_seq", sequenceName = "funcionario_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @MapsId
    @OneToOne(mappedBy = "funcionario")
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


    @NotNull
    @Column(name = "estado_logico", nullable = false)
    private Boolean estadoLogico = true;


    public Funcionario(FuncionarioFrontCriacao dto, Pessoa pessoa) {

        this.pessoa = pessoa;
        this.vagas = new ArrayList<>();

        this.departamento = dto.getDepartamento();
        this.funcao = dto.getFuncao();

        this.estadoLogico = true;
    }
}
