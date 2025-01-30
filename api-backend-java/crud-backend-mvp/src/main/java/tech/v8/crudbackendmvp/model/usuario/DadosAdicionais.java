package tech.v8.crudbackendmvp.model.usuario;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.v8.crudbackendmvp.model.enums.ExFuncionario;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@Table(name = "dados_adicionais")
public class DadosAdicionais {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dados_adicionais_seq")
    @SequenceGenerator(name = "dados_adicionais_seq", sequenceName = "dados_adicionais_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "candidato_id")
    private Candidato candidato;

    @NotBlank
    @Size(max = 255)
    @Column(name = "indicacao_vaga", nullable = false)
    private String indicacaoVaga;

    @NotBlank
    @Size(max = 255)
    @Column(name = "email_indicacao", nullable = false)
    private String indicacaoEmail;

    @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(name = "ex_funcionario", nullable = false)
    private ExFuncionario exFuncionario;

    @NotBlank
    @Column(name = "pretensao_salarial", precision = 10, scale = 2, nullable = false)
    private BigDecimal pretensaoSalarial;

    @NotBlank
    @Size(max = 255)
    @Column(name = "fonte_vaga", nullable = false)
    private String fonteVaga;
}
