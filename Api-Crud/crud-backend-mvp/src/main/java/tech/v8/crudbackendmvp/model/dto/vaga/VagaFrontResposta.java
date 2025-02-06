package tech.v8.crudbackendmvp.model.dto.vaga;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.v8.crudbackendmvp.model.dto.prova.ProvaFrontResumo;
import tech.v8.crudbackendmvp.model.dto.usuario.funcionario.FuncionarioFrontResumo;
import tech.v8.crudbackendmvp.model.vaga.Vaga;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class VagaFrontResposta {
    private Long id;

    private FuncionarioFrontResumo responsavel;

    private List<ProvaFrontResumo> provas;

    private String nome;
    private String tipo;
    private String localidade;
    private String modelo;
    private String descricao;
    private String responsabilidade;
    private String requisitos;
    @JsonProperty("faixa_salarial")
    private BigDecimal faixaSalarial;
    @JsonProperty("regime_contratacao")
    private String regimeContratacao;
    private String beneficios;
    private String status;
    @JsonProperty("quantidade_vagas")
    private Integer quantidadeVagas;
    private String atribuicoes;
    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    @JsonProperty("data_criacao")
    private LocalDateTime dataCriacao;

    public VagaFrontResposta(Vaga vaga) {
        this.id = vaga.getId();

        this.responsavel = new FuncionarioFrontResumo(vaga.getResponsavel());

        this.provas = vaga.getProvas().stream().map(ProvaFrontResumo::new).toList();

        this.nome = vaga.getNome();
        this.tipo = vaga.getTipo();
        this.localidade = vaga.getLocalidade();
        this.modelo = vaga.getModelo().name();
        this.descricao = vaga.getDescricao();
        this.responsabilidade = vaga.getResponsabilidade();
        this.requisitos = vaga.getRequisitos();
        this.faixaSalarial = vaga.getFaixaSalarial();
        this.regimeContratacao = vaga.getRegimeContratacao().name();
        this.beneficios = vaga.getBeneficios();
        this.status = vaga.getStatus().name();
        this.quantidadeVagas = vaga.getQtdVagas();
        this.atribuicoes = vaga.getAtribuicoes();
        this.dataCriacao = vaga.getDataCriacao();
    }
}
