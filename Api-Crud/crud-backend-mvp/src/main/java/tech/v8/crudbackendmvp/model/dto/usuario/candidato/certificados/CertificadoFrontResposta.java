package tech.v8.crudbackendmvp.model.dto.usuario.candidato.certificados;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.CandidatoFrontResumo;
import tech.v8.crudbackendmvp.model.usuario.Certificado;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CertificadoFrontResposta {
    private Long id;
    private CandidatoFrontResumo candidato;
    private String nome;
    @JsonProperty("dt_Emissao")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dtEmissao;
    @JsonProperty("dt_Vencimento")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dtVencimento;

    public CertificadoFrontResposta(Certificado certificado) {
        this.id = certificado.getId();
        this.candidato = new CandidatoFrontResumo(certificado.getCandidato());
        this.nome = certificado.getNome();
        this.dtEmissao = certificado.getDataEmissao();
        this.dtVencimento = certificado.getDataVencimento();
    }
}
