package com.mvp.backend.backendmvp.controller;

import com.mvp.backend.backendmvp.model.dto.candidato.CandidatoFrontDTOCriacao;
import com.mvp.backend.backendmvp.model.dto.candidato.CandidatoFrontDTOEdicao;
import com.mvp.backend.backendmvp.model.dto.candidato.CandidatoFrontDTOResposta;
import com.mvp.backend.backendmvp.model.dto.candidato.CandidatoPageDTO;
import com.mvp.backend.backendmvp.service.CandidatoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/crud/candidatos")
public class CandidatoController {

    private CandidatoService candidatoService;

    @GetMapping
    public CandidatoPageDTO list(@RequestParam(defaultValue = "0") @PositiveOrZero int page,
                                 @RequestParam(defaultValue = "10") @Positive @NotNull int size) {
        return candidatoService.list(page, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CandidatoFrontDTOResposta create(@RequestBody @Valid CandidatoFrontDTOCriacao dto) {
        return candidatoService.create(dto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CandidatoFrontDTOResposta findById(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id) {
        return candidatoService.findById(id);

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CandidatoFrontDTOResposta update(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id,
            @RequestBody @Valid CandidatoFrontDTOEdicao dto) {
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
