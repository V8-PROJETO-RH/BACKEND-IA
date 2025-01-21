package com.mvp.backend.backendmvp.controller;

import com.mvp.backend.backendmvp.model.dto.vaga.VagaFrontDTOCriacao;
import com.mvp.backend.backendmvp.model.dto.vaga.VagaFrontDTOEdicao;
import com.mvp.backend.backendmvp.model.dto.vaga.VagaFrontDTOResposta;
import com.mvp.backend.backendmvp.model.dto.vaga.VagaPageDTO;
import com.mvp.backend.backendmvp.service.VagaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/crud/vagas")
public class VagaController {

    private VagaService vagaService;

    @GetMapping
    public List<VagaFrontDTOResposta> list() {
        return vagaService.list();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VagaFrontDTOResposta create(@RequestBody @Valid VagaFrontDTOCriacao dto) {
        return vagaService.create(dto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VagaFrontDTOResposta findById(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id) {
        return vagaService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VagaFrontDTOResposta update(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id,
            @RequestBody @Valid VagaFrontDTOEdicao dto) {
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
