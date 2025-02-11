package tech.v8.crudbackendmvp.model.usuario;


import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import tech.v8.crudbackendmvp.infra.validation.Email;
import tech.v8.crudbackendmvp.model.dto.usuario.pessoa.PessoaFrontCriacao;
import tech.v8.crudbackendmvp.model.enums.Role;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "pessoa")
@SQLDelete(sql = "UPDATE pessoa SET estado_logico = false WHERE id = ?")
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "candidato_seq")
    @SequenceGenerator(name = "candidato_seq", sequenceName = "candidato_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_funcionario")
    private Funcionario funcionario;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_candidato")
    private Candidato candidato;

    @NotBlank
    @Size(max = 255)
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotBlank
    @Email(message = "endereço de email inválido")
    @Size(max = 255)
    @Column(name = "email", nullable = false)
    private String email;

    @NotBlank
    @Size(max = 255)
    @Column(name = "cpf", nullable = false)
    private String cpf;

    @NotNull
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @NotNull
    @Enumerated(EnumType.STRING) // Converter para String no banco
    @Column(name = "role", length = 45)
    private Role role;

    @NotNull
    @Column(name = "estado_logico", nullable = false)
    @ColumnDefault("true")
    private Boolean estadoLogico;

    public Pessoa(PessoaFrontCriacao dto) {
        this.funcionario = null;
        this.candidato = null;

        this.nome = dto.getNome();
        this.email = dto.getEmail();
        this.cpf = dto.getCpf();
        this.dataNascimento = dto.getDataNascimento();
        this.estadoLogico = true;
    }

    public void setRole(String role) {
        try {
            this.role = Role.fromString(role);
        } catch (IllegalArgumentException e) {
            throw new ConstraintViolationException(e.getMessage(), null); // Lança exceção personalizada
        }
    }
}
