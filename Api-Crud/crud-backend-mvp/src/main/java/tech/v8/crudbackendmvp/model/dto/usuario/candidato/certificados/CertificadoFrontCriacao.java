package tech.v8.crudbackendmvp.model.dto.usuario.candidato.certificados;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class CertificadoFrontCriacao {
    private String nome;
    @JsonProperty("dt_Emissao")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dtEmissao;
    @JsonProperty("dt_Vencimento")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dtVencimento;
}
