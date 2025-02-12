package tech.v8.crudbackendmvp.controller.usuario;

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
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.certificados.CertificadoFrontCriacao;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.certificados.CertificadoFrontResposta;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.experiencias.ExperienciaFrontCriacao;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.experiencias.ExperienciaFrontResposta;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.formacoes.FormacaoFrontCriacao;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.formacoes.FormacaoFrontResposta;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.habilidades.HabilidadeFrontCriacao;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.habilidades.HabilidadeFrontResposta;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.idiomas.IdiomaFrontCriacao;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.idiomas.IdiomaFrontResposta;
import tech.v8.crudbackendmvp.model.dto.vagaaplicada.VagaAplicadaFrontResposta;
import tech.v8.crudbackendmvp.service.usuario.*;

import java.util.List;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/candidatos")
public class CandidatoController {

    private CandidatoService candidatoService;
    private HabilidadeService habilidadeService;
    private CertificadoService certificadoService;
    private IdiomaService idiomaService;
    private FormacaoService formacaoService;
    private ExperienciaService experienciaService;

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

    @GetMapping("/{id}/vagas-aplicadas")
    @ResponseStatus(HttpStatus.OK)
    public List<VagaAplicadaFrontResposta> findVagasAplicadasById(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id) {
        return candidatoService.findVagasAplicadasDTOById(id);

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

    /// endpoints para detalhes habilidades
    @GetMapping("/{id}/habilidades")
    @ResponseStatus(HttpStatus.OK)
    public List<HabilidadeFrontResposta> findHabilidadesById(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id) {
        return habilidadeService.findHabilidadesByCandidatoId(id);
    }

    @PostMapping("/{id}/habilidades")
    @ResponseStatus(HttpStatus.CREATED)
    public HabilidadeFrontResposta createHabilidade(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id,
            @RequestBody @Valid HabilidadeFrontCriacao dto) {
        return habilidadeService.create(id, dto);
    }

    @DeleteMapping("/{id}/habilidades/{idHabilidade}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHabilidade(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id,
            @PathVariable
            @NotNull(message = "o parâmetro idHabilidade não pode ser nulo.")
            @Positive(message = "o parâmetro idHabilidade deve ser positivo.")
            Long idHabilidade) {
        habilidadeService.delete(id, idHabilidade);
    }

    @PutMapping("/{id}/habilidades/{idHabilidade}")
    @ResponseStatus(HttpStatus.OK)
    public HabilidadeFrontResposta updateHabilidade(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id,
            @PathVariable
            @NotNull(message = "o parâmetro idHabilidade não pode ser nulo.")
            @Positive(message = "o parâmetro idHabilidade deve ser positivo.")
            Long idHabilidade,
            @RequestBody @Valid HabilidadeFrontCriacao dto) {
        return habilidadeService.update(idHabilidade, dto);
    }

    /// endpoints certificados
    @GetMapping("/{id}/certificados")
    @ResponseStatus(HttpStatus.OK)
    public List<CertificadoFrontResposta> findCertificadosById(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id) {
        return certificadoService.findCertificadosByCandidatoId(id);
    }

    @PostMapping("/{id}/certificados")
    @ResponseStatus(HttpStatus.CREATED)
    public CertificadoFrontResposta createCertificado(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id,
            @RequestBody @Valid CertificadoFrontCriacao dto) {
        return certificadoService.create(id, dto);
    }

    @DeleteMapping("/{id}/certificados/{idCertificado}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCertificado(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id,
            @PathVariable
            @NotNull(message = "o parâmetro idCertificado não pode ser nulo.")
            @Positive(message = "o parâmetro idCertificado deve ser positivo.")
            Long idCertificado) {
        certificadoService.delete(id, idCertificado);
    }

    @PutMapping("/{id}/certificados/{idCertificado}")
    @ResponseStatus(HttpStatus.OK)
    public CertificadoFrontResposta updateCertificado(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id,
            @PathVariable
            @NotNull(message = "o parâmetro idCertificado não pode ser nulo.")
            @Positive(message = "o parâmetro idCertificado deve ser positivo.")
            Long idCertificado,
            @RequestBody @Valid CertificadoFrontCriacao dto) {
        return certificadoService.update(idCertificado, dto);
    }

    /// endpoints idiomas
    @GetMapping("/{id}/idiomas")
    @ResponseStatus(HttpStatus.OK)
    public List<IdiomaFrontResposta> findIdiomasById(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id) {
        return idiomaService.findAllByCandidatoId(id);
    }

    @PostMapping("/{id}/idiomas")
    @ResponseStatus(HttpStatus.CREATED)
    public IdiomaFrontResposta createIdioma(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id,
            @RequestBody @Valid IdiomaFrontCriacao dto) {
        return idiomaService.create(id, dto);
    }

    @DeleteMapping("/{id}/idiomas/{idIdioma}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIdioma(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id,
            @PathVariable
            @NotNull(message = "o parâmetro idIdioma não pode ser nulo.")
            @Positive(message = "o parâmetro idIdioma deve ser positivo.")
            Long idIdioma) {
        idiomaService.delete(id, idIdioma);
    }

    @PutMapping("/{id}/idiomas/{idIdioma}")
    @ResponseStatus(HttpStatus.OK)
    public IdiomaFrontResposta updateIdioma(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id,
            @PathVariable
            @NotNull(message = "o parâmetro idIdioma não pode ser nulo.")
            @Positive(message = "o parâmetro idIdioma deve ser positivo.")
            Long idIdioma,
            @RequestBody @Valid IdiomaFrontCriacao dto) {
        return idiomaService.update(idIdioma, dto);
    }

    /// endpoints formação
    @GetMapping("/{id}/formacoes")
    @ResponseStatus(HttpStatus.OK)
    public List<FormacaoFrontResposta> findFormacoesById(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id) {
        return formacaoService.findFormacoesByCandidatoId(id);
    }

    @PostMapping("/{id}/formacoes")
    @ResponseStatus(HttpStatus.CREATED)
    public FormacaoFrontResposta createFormacao(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id,
            @RequestBody @Valid FormacaoFrontCriacao dto) {
        return formacaoService.create(id, dto);
    }

    @DeleteMapping("/{id}/formacoes/{idFormacao}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFormacao(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id,
            @PathVariable
            @NotNull(message = "o parâmetro idFormacao não pode ser nulo.")
            @Positive(message = "o parâmetro idFormacao deve ser positivo.")
            Long idFormacao) {
        formacaoService.delete(id, idFormacao);
    }

    @PutMapping("/{id}/formacoes/{idFormacao}")
    @ResponseStatus(HttpStatus.OK)
    public FormacaoFrontResposta updateFormacao(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id,
            @PathVariable
            @NotNull(message = "o parâmetro idFormacao não pode ser nulo.")
            @Positive(message = "o parâmetro idFormacao deve ser positivo.")
            Long idFormacao,
            @RequestBody @Valid FormacaoFrontCriacao dto) {
        return formacaoService.update(idFormacao, dto);
    }

    /// endpoints experiencia
    @GetMapping("/{id}/experiencias")
    @ResponseStatus(HttpStatus.OK)
    public List<ExperienciaFrontResposta> findExperienciasById(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id) {
        return experienciaService.findExperienciasByCandidatoId(id);
    }

    @PostMapping("/{id}/experiencias")
    @ResponseStatus(HttpStatus.CREATED)
    public ExperienciaFrontResposta createExperiencia(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id,
            @RequestBody @Valid ExperienciaFrontCriacao dto) {
        return experienciaService.create(id, dto);
    }

    @DeleteMapping("/{id}/experiencias/{idExperiencia}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExperiencia(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id,
            @PathVariable
            @NotNull(message = "o parâmetro idExperiencia não pode ser nulo.")
            @Positive(message = "o parâmetro idExperiencia deve ser positivo.")
            Long idExperiencia) {
        experienciaService.delete(id, idExperiencia);
    }

    @PutMapping("/{id}/experiencias/{idExperiencia}")
    @ResponseStatus(HttpStatus.OK)
    public ExperienciaFrontResposta updateExperiencia(
            @PathVariable
            @NotNull(message = "o parâmetro id não pode ser nulo.")
            @Positive(message = "o parâmetro id deve ser positivo.")
            Long id,
            @PathVariable
            @NotNull(message = "o parâmetro idExperiencia não pode ser nulo.")
            @Positive(message = "o parâmetro idExperiencia deve ser positivo.")
            Long idExperiencia,
            @RequestBody @Valid ExperienciaFrontCriacao dto) {
        return experienciaService.update(idExperiencia, dto);
    }


}
