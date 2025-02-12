package tech.v8.crudbackendmvp.model.dto.usuario.candidato;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tech.v8.crudbackendmvp.infra.validation.Telefone;
import tech.v8.crudbackendmvp.model.dto.usuario.pessoa.PessoaFrontCriacao;

@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class CandidatoFrontCriacao extends PessoaFrontCriacao {

    @NotBlank(message = "O nome completo do candidato não pode estar vazio.")
    @JsonProperty("nome_completo")
    private String nomeCompleto;

    @NotBlank(message = "o telefone do candidato não pode estar vazio.")
    @Telefone
    private String telefone;

    @NotBlank(message = "o linkedin do candidato não pode estar vazio.")
    @JsonProperty("perfil_linkedin")
    private String perfilLinkedin;

    @NotBlank(message = "o genero do candidato não pode estar vazio.")
    private String genero;

    @NotBlank(message = "o endereco do candidato não pode estar vazio.")
    private String endereco;

    @NotBlank(message = "o numero do candidato não pode estar vazio.")
    private String numero;

    @NotBlank(message = "o complemento do candidato não pode estar vazio.")
    private String complemento;

    @NotBlank(message = "o bairro do candidato não pode estar vazio.")
    private String bairro;

    @NotBlank(message = "a cidade candidato não pode estar vazia.")
    private String cidade;

    @NotBlank(message = "o estado do candidato não pode estar vazio.")
    private String estado;
}
