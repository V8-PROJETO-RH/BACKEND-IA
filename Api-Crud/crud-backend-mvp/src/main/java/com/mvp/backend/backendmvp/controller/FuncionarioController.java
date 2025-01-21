package com.mvp.backend.backendmvp.controller;

import com.mvp.backend.backendmvp.model.dto.funcionario.FuncionarioFrontDTOCriacao;
import com.mvp.backend.backendmvp.model.dto.funcionario.FuncionarioFrontDTOEdicao;
import com.mvp.backend.backendmvp.model.dto.funcionario.FuncionarioFrontDTOResposta;
import com.mvp.backend.backendmvp.model.dto.funcionario.FuncionarioPageDTO;
import com.mvp.backend.backendmvp.service.FuncionarioService;
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
@RequestMapping("/crud/funcionarios")
public class FuncionarioController {

    private FuncionarioService funcionarioService;

    @GetMapping
    public List<FuncionarioFrontDTOResposta> list() {
        return funcionarioService.list();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FuncionarioFrontDTOResposta create(@RequestBody @Valid FuncionarioFrontDTOCriacao dto){
        return funcionarioService.create(dto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FuncionarioFrontDTOResposta findById(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id) {
            return funcionarioService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FuncionarioFrontDTOResposta update(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id,
            @RequestBody @Valid FuncionarioFrontDTOEdicao dto) {
        return funcionarioService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id) {
        funcionarioService.delete(id);
    }

}
