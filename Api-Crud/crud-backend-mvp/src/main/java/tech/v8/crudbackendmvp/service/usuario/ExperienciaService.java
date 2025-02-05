package tech.v8.crudbackendmvp.service.usuario;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.v8.crudbackendmvp.exception.ResourceNotFoundException;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.experiencias.ExperienciaFrontCriacao;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.experiencias.ExperienciaFrontResposta;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.experiencias.ExperienciaMapper;
import tech.v8.crudbackendmvp.model.usuario.Candidato;
import tech.v8.crudbackendmvp.model.usuario.DetalhesExperiencias;
import tech.v8.crudbackendmvp.repository.usuario.DetalhesExperienciasRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ExperienciaService {

    private final DetalhesExperienciasRepository experienciasRepository;
    private final CandidatoService candidatoService;

    public List<ExperienciaFrontResposta> findExperienciasByCandidatoId(Long candidatoId) {
        return experienciasRepository.findAllByCandidatoId(candidatoId).stream()
                .map(ExperienciaMapper::toDTO)
                .toList();
    }

    public DetalhesExperiencias getExperienciaReferenceById(Long id) {
        return experienciasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Experiência de id " + id + " não encontrada."));
    }

    public ExperienciaFrontResposta create(Long idCandidato, ExperienciaFrontCriacao experiencia) {
        Candidato candidatoEncontrado = candidatoService.getCandidatoReferenceById(idCandidato);
        DetalhesExperiencias experienciaNova = ExperienciaMapper.toExperiencia(experiencia, candidatoEncontrado);
        DetalhesExperiencias experienciaSalva = experienciasRepository.save(experienciaNova);

        candidatoEncontrado.getDetalhesExperiencia().add(experienciaSalva);

        return ExperienciaMapper.toDTO(experienciaSalva);

    }

    public ExperienciaFrontResposta update(Long idExperiencia, ExperienciaFrontCriacao experiencia) {
        DetalhesExperiencias experienciaEncontrada = getExperienciaReferenceById(idExperiencia);

        experienciaEncontrada.setTitulo(experiencia.getTitulo());
        experienciaEncontrada.setEmpresa(experiencia.getEmpresa());
        experienciaEncontrada.setDescricao(experiencia.getDescricao());
        experienciaEncontrada.setLocalidade(experiencia.getLocalidade());
        experienciaEncontrada.setModelo(experiencia.getModelo());
        experienciaEncontrada.setCompetencias(experiencia.getCompetencias());
        experienciaEncontrada.setDataInicio(experiencia.getDtInicio());
        experienciaEncontrada.setDataFinal(experiencia.getDtFinal());

        DetalhesExperiencias experienciaSalva = experienciasRepository.save(experienciaEncontrada);
        return ExperienciaMapper.toDTO(experienciaSalva);
    }

    public void delete(Long idCandidato, Long idExperiencia) {
        Candidato candidatoEncontrado = candidatoService.getCandidatoReferenceById(idCandidato);
        DetalhesExperiencias experienciaEncontrada = getExperienciaReferenceById(idExperiencia);

        candidatoEncontrado.getDetalhesExperiencia().remove(experienciaEncontrada);
        experienciasRepository.delete(experienciaEncontrada);
    }
}
