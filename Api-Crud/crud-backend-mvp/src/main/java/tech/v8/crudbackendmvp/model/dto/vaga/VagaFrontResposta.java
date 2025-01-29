package tech.v8.crudbackendmvp.model.dto.vaga;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.v8.crudbackendmvp.model.Vaga;
import tech.v8.crudbackendmvp.model.dto.funcionario.FuncionarioFrontResposta;
import tech.v8.crudbackendmvp.model.dto.funcionario.FuncionarioMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class VagaFrontResposta {
    private Long id;
    private String contratacao;
    private String descricao;
    private String modelo;
    private String nome;
    private Integer quantidade;
    private String requisitos;
    private String responsavel;
    private BigDecimal salario;
    private String status;
    private FuncionarioFrontResposta funcionario;
    private String atribuicoes;
    private String beneficios;
    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private LocalDateTime dataCriacao;

    public VagaFrontResposta(Vaga vaga) {
        this.id = vaga.getId();
        this.contratacao = vaga.getContratacao();
        this.descricao = vaga.getDescricao();
        this.modelo = vaga.getModelo();
        this.nome = vaga.getNome();
        this.quantidade = vaga.getQtd();
        this.requisitos = vaga.getRequisitos();
        this.responsavel = vaga.getResponsavel();
        this.salario = vaga.getSalario();
        this.status = vaga.getStatus().name();
        this.funcionario = FuncionarioMapper.toDTO(vaga.getFuncionario());
        this.atribuicoes = vaga.getAtribuicoes();
        this.beneficios = vaga.getBeneficios();
        this.dataCriacao = vaga.getDataCriacao();
    }
}
