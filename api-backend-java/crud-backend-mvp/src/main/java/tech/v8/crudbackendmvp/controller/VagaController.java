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
import tech.v8.crudbackendmvp.model.dto.vaga.VagaFrontCriacao;
import tech.v8.crudbackendmvp.model.dto.vaga.VagaFrontEdicao;
import tech.v8.crudbackendmvp.model.dto.vaga.VagaFrontResposta;
import tech.v8.crudbackendmvp.model.dto.vaga.VagaPage;
import tech.v8.crudbackendmvp.service.VagaService;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/crud/vagas")
public class VagaController {

    private VagaService vagaService;

    @GetMapping
    public VagaPage list(@RequestParam(defaultValue = "0") @PositiveOrZero int page,
                         @RequestParam(defaultValue = "10") @Positive @Max(50) int size
    ) {
        return vagaService.list(page, size);
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
}
