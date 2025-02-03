package tech.v8.crudbackendmvp.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.CandidatoFrontCriacao;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.CandidatoFrontEdicao;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.CandidatoFrontResposta;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.CandidatoPage;
import tech.v8.crudbackendmvp.service.usuario.CandidatoService;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/crud/candidatos")
public class CandidatoController {

    private CandidatoService candidatoService;

    @GetMapping
    public CandidatoPage list(@RequestParam(defaultValue = "0") @PositiveOrZero int page,
                              @RequestParam(defaultValue = "10") @Positive @NotNull int size) {
        return candidatoService.list(page, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CandidatoFrontResposta create(@RequestBody @Valid CandidatoFrontCriacao dto) {
        return candidatoService.create(dto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CandidatoFrontResposta findById(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id) {
        return candidatoService.findDTOById(id);

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CandidatoFrontResposta update(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id,
            @RequestBody @Valid CandidatoFrontEdicao dto) {
        return candidatoService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id) {
        candidatoService.delete(id);
    }
}
