package tech.v8.crudbackendmvp.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.v8.crudbackendmvp.model.dto.usuario.funcionario.FuncionarioFrontCriacao;
import tech.v8.crudbackendmvp.model.dto.usuario.funcionario.FuncionarioFrontEdicao;
import tech.v8.crudbackendmvp.model.dto.usuario.funcionario.FuncionarioFrontResposta;
import tech.v8.crudbackendmvp.service.usuario.FuncionarioService;

import java.util.List;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/crud/funcionarios")
public class FuncionarioController {

    private FuncionarioService funcionarioService;

    @GetMapping
    public List<FuncionarioFrontResposta> list() {
        return funcionarioService.list();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FuncionarioFrontResposta create(@RequestBody @Valid FuncionarioFrontCriacao dto){
        return funcionarioService.create(dto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FuncionarioFrontResposta findById(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id) {
        return funcionarioService.findDTOById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FuncionarioFrontResposta update(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id,
            @RequestBody @Valid FuncionarioFrontEdicao dto) {
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
