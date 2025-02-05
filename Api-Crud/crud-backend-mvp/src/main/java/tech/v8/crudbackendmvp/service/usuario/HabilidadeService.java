package tech.v8.crudbackendmvp.service.usuario;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.v8.crudbackendmvp.exception.ResourceNotFoundException;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.habilidades.HabilidadeFrontCriacao;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.habilidades.HabilidadeFrontResposta;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.habilidades.HabilidadeMapper;
import tech.v8.crudbackendmvp.model.usuario.Candidato;
import tech.v8.crudbackendmvp.model.usuario.DetalhesHabilidades;
import tech.v8.crudbackendmvp.repository.usuario.DetalhesHabilidadesRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class HabilidadeService {

    private final CandidatoService candidatoService;
    private final DetalhesHabilidadesRepository habilidadesRepository;

    public List<HabilidadeFrontResposta> findHabilidadesByCandidatoId(Long candidatoId) {
        return habilidadesRepository.findAllByCandidatoId(candidatoId).stream()
                .map(HabilidadeMapper::toDTO)
                .toList();
    }

    public DetalhesHabilidades getHabilidadeReferenceById(Long id) {
        return habilidadesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Habilidade de id " + id + " não encontrada."));
    }

    public HabilidadeFrontResposta create(Long idCandidato, HabilidadeFrontCriacao habilidade) {
        Candidato candidatoEncontrado = candidatoService.getCandidatoReferenceById(idCandidato);
        DetalhesHabilidades habilidadeNova = HabilidadeMapper.toHabilidade(habilidade, candidatoEncontrado);
        DetalhesHabilidades habilidadeSalva = habilidadesRepository.save(habilidadeNova);

        candidatoEncontrado.getDetalhesHabilidades().add(habilidadeSalva);

        return HabilidadeMapper.toDTO(habilidadeSalva);

    }

    public HabilidadeFrontResposta update(Long idHabilidade, HabilidadeFrontCriacao habilidade) {
        DetalhesHabilidades habilidadeEncontrada = getHabilidadeReferenceById(idHabilidade);

        habilidadeEncontrada.setHabilidade(habilidade.getHabilidade());
        DetalhesHabilidades habilidadeSalva = habilidadesRepository.save(habilidadeEncontrada);
        return HabilidadeMapper.toDTO(habilidadeSalva);
    }

    public void delete(Long idCandidato, Long idHabilidade) {
        Candidato candidatoEncontrado = candidatoService.getCandidatoReferenceById(idCandidato);
        DetalhesHabilidades habilidadeEncontrada = getHabilidadeReferenceById(idHabilidade);

        candidatoEncontrado.getDetalhesHabilidades().remove(habilidadeEncontrada);
        habilidadesRepository.delete(habilidadeEncontrada);
    }

}
