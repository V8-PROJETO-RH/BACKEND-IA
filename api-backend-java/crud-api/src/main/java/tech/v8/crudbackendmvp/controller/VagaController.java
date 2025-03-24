package tech.v8.crudbackendmvp.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.v8.crudbackendmvp.model.dto.prova.ProvaFrontCriacao;
import tech.v8.crudbackendmvp.model.dto.prova.ProvaFrontResposta;
import tech.v8.crudbackendmvp.model.dto.vaga.VagaFrontCriacao;
import tech.v8.crudbackendmvp.model.dto.vaga.VagaFrontEdicao;
import tech.v8.crudbackendmvp.model.dto.vaga.VagaFrontResposta;
import tech.v8.crudbackendmvp.model.dto.vaga.VagaPage;
import tech.v8.crudbackendmvp.model.dto.vagaaplicada.VagaAplicadaFrontResposta;
import tech.v8.crudbackendmvp.service.ProvaService;
import tech.v8.crudbackendmvp.service.VagaService;

import java.util.List;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/vagas")
public class VagaController {

    private VagaService vagaService;
    private ProvaService provaService;

    @GetMapping
    public VagaPage list(@RequestParam(defaultValue = "0") @PositiveOrZero int page,
                         @RequestParam(defaultValue = "10") @Positive @Max(50) int size
    ) {
        return vagaService.list(page, size);
    }

    @GetMapping("/search")
    public VagaPage searchVagas(
            @RequestParam(value = "nome_like", required = false) String nome,
            @RequestParam(value = "modelo_like", required = false) String modelo,
            @RequestParam(value = "local_like", required = false) String local,
            @RequestParam(value = "status_like", required = false) String status,

            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive  int size

    ){
        return vagaService.search(nome, modelo, local, status, page, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VagaFrontResposta create(@RequestBody @Valid VagaFrontCriacao dto) {
        return vagaService.create(dto);
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VagaFrontResposta findById(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id) {
        return vagaService.findDTOById(id);
    }

    @GetMapping("/{id}/vagas-aplicadas")
    @ResponseStatus(HttpStatus.OK)
    public List<VagaAplicadaFrontResposta> findVagasAplicadasById(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id) {
        return vagaService.findVagasAplicadasDTOById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VagaFrontResposta update(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id,
            @RequestBody @Valid VagaFrontEdicao dto) {
        return vagaService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id) {
        vagaService.delete(id);
    }

    /// Endpoints para a prova.

    @GetMapping("/{id}/provas")
    @ResponseStatus(HttpStatus.OK)
    public List<ProvaFrontResposta> findProvasById(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id) {
        return provaService.findProvasDTOByVagaId(id);
    }

    @PostMapping("/{id}/provas")
    @ResponseStatus(HttpStatus.CREATED)
    public ProvaFrontResposta createProva(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id,
            @RequestBody @Valid
            ProvaFrontCriacao prova) {
        return provaService.create(id, prova);
    }

    @PutMapping("/{id}/provas/{provaId}")
    @ResponseStatus(HttpStatus.OK)
    public ProvaFrontResposta updateProva(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id,
            @PathVariable
            @NotNull(message = "o parâmetro provaId não pode ser nulo.")
            @Positive(message = "o parâmetro provaId deve ser positivo.")
            Long provaId, @RequestBody @Valid ProvaFrontCriacao prova) {
        return provaService.update(provaId, prova);
    }

    @DeleteMapping("/{id}/provas/{provaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProva(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id,
            @PathVariable
            @NotNull(message = "o parâmetro provaId não pode ser nulo.")
            @Positive(message = "o parâmetro provaId deve ser positivo.")
            Long provaId) {
        provaService.delete(provaId);
    }
}
