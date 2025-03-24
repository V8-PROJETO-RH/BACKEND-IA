package tech.v8.crudbackendmvp.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.v8.crudbackendmvp.model.dto.resultado.ResultadoFrontCriacao;
import tech.v8.crudbackendmvp.model.dto.resultado.ResultadoFrontResposta;
import tech.v8.crudbackendmvp.model.dto.vagaaplicada.VagaAplicadaFrontCriacao;
import tech.v8.crudbackendmvp.model.dto.vagaaplicada.VagaAplicadaFrontEdicao;
import tech.v8.crudbackendmvp.model.dto.vagaaplicada.VagaAplicadaFrontResposta;
import tech.v8.crudbackendmvp.model.dto.vagaaplicada.VagaAplicadaPage;
import tech.v8.crudbackendmvp.service.ResultadoFinalService;
import tech.v8.crudbackendmvp.service.VagaAplicadaService;


@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/vagas-aplicadas")
public class VagasAplicadasController {
    private VagaAplicadaService vagaAplicadaService;
    private ResultadoFinalService resultadoService;

    @GetMapping
    public VagaAplicadaPage list(@RequestParam(defaultValue = "0") @PositiveOrZero int page,
                                 @RequestParam(defaultValue = "10") @Positive int size
    ) {
        return vagaAplicadaService.list(page, size);
    }

    @GetMapping("/search")
    public VagaAplicadaPage searchVagasAplicadas(
            @RequestParam(value = "status_like", required = false) String status,

            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int size
    ){
        return vagaAplicadaService.search(status, page, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VagaAplicadaFrontResposta create(@RequestBody @Valid VagaAplicadaFrontCriacao dto) {
        return vagaAplicadaService.create(dto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VagaAplicadaFrontResposta findById(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id) {
        return vagaAplicadaService.findDTOById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VagaAplicadaFrontResposta update(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id,
            @RequestBody @Valid VagaAplicadaFrontEdicao dto) {
        return vagaAplicadaService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id) {
        vagaAplicadaService.delete(id);
    }

    /// endpoints do resultado

    @GetMapping("/{id}/resultados")
    @ResponseStatus(HttpStatus.OK)
    public ResultadoFrontResposta findResultadoById(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id) {
        return vagaAplicadaService.findResultado(id);
    }

    @PostMapping("/{id}/resultados")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultadoFrontResposta createResultado(@PathVariable
                                                  @NotNull(message = "o parâmetro id não pode ser nulo.")
                                                  @Positive(message = "o parâmetro id deve ser positivo.")
                                                          Long id,
                                                  @RequestBody @Valid ResultadoFrontCriacao dto) {
        return resultadoService.createResultado(id, dto);
    }

}
