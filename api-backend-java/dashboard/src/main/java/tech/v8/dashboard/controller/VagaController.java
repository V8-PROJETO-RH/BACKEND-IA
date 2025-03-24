package tech.v8.dashboard.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tech.v8.dashboard.model.dto.VagasAbertasDTO;
import tech.v8.dashboard.model.dto.VagasCandidatosAprovadosDTO;
import tech.v8.dashboard.model.dto.VagasFechadasDTO;
import tech.v8.dashboard.service.VagaService;


@RestController
@RequestMapping("/dashboard/vagas")
@AllArgsConstructor
public class VagaController {

    private final VagaService service;

    @GetMapping("/abertas")
    @ResponseStatus(HttpStatus.OK)
    public VagasAbertasDTO obterVagasAbertas() {
        return service.obterVagasAbertas();
    }

    @GetMapping("/fechadas")
    @ResponseStatus(HttpStatus.OK)
    public VagasFechadasDTO obterVagasFechadas() {
        return service.obterVagasFechadas();
    }

    @GetMapping("/candidatos-aprovados")
    @ResponseStatus(HttpStatus.OK)
    public VagasCandidatosAprovadosDTO obterVagasCandidadosAprovados() {
        return service.obterVagasCandidatosAprovados();
    }
}
