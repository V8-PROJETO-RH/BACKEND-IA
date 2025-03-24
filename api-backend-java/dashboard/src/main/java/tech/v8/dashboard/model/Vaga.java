package tech.v8.dashboard.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Vaga {
    private Integer id;
    private String nome;
    private String tipo;
    private String localidade;
    private String modelo;
    @JsonProperty("data_criacao")
    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private LocalDateTime criacao;
    @JsonProperty("quantidade_vagas")
    private Integer quantidade;
    private Responsavel responsavel;


public record Responsavel(
        Integer id,
        String nome,
        String role
){}
}
