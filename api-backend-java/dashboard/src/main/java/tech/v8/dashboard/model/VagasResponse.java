package tech.v8.dashboard.model;

import lombok.Data;

import java.util.List;

@Data
public class VagasResponse {

    private List<Vaga> vagas;
    private int totalPages;
    private long totalElements;
}
