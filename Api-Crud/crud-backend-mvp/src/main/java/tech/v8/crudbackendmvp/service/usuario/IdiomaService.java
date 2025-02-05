package tech.v8.crudbackendmvp.service.usuario;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.v8.crudbackendmvp.exception.ResourceNotFoundException;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.idiomas.IdiomaFrontCriacao;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.idiomas.IdiomaFrontResposta;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.idiomas.IdiomaMapper;
import tech.v8.crudbackendmvp.model.usuario.Candidato;
import tech.v8.crudbackendmvp.model.usuario.DetalhesIdiomas;
import tech.v8.crudbackendmvp.repository.usuario.DetalhesIdiomasRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class IdiomaService {

    private final DetalhesIdiomasRepository idiomaRepository;
    private final CandidatoService candidatoService;

    public List<IdiomaFrontResposta> findAllByCandidatoId(Long candidatoId) {
        return idiomaRepository.findAllByCandidatoId(candidatoId).stream()
                .map(IdiomaMapper::toDTO)
                .toList();
    }

    public DetalhesIdiomas getReferenceById(Long id) {
        return idiomaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Idioma de id " + id + " não encontrado."));
    }

    public IdiomaFrontResposta create(Long idCandidato, IdiomaFrontCriacao idioma) {
        Candidato candidatoEncontrado = candidatoService.getCandidatoReferenceById(idCandidato);
        DetalhesIdiomas idiomaNovo = IdiomaMapper.toIdioma(idioma, candidatoEncontrado);
        DetalhesIdiomas idiomaSalvo = idiomaRepository.save(idiomaNovo);

        candidatoEncontrado.getDetalhesIdiomas().add(idiomaSalvo);

        return IdiomaMapper.toDTO(idiomaSalvo);

    }

    public IdiomaFrontResposta update(Long idIdioma, IdiomaFrontCriacao idioma) {
        DetalhesIdiomas idiomaEncontrado = getReferenceById(idIdioma);

        idiomaEncontrado.setNome(idioma.getNome());
        idiomaEncontrado.setProficiencia(idioma.getProficiencia());
        DetalhesIdiomas idiomaSalvo = idiomaRepository.save(idiomaEncontrado);
        return IdiomaMapper.toDTO(idiomaSalvo);
    }

    public void delete(Long idCandidato, Long idIdioma) {
        Candidato candidatoEncontrado = candidatoService.getCandidatoReferenceById(idCandidato);
        DetalhesIdiomas idiomaEncontrado = getReferenceById(idIdioma);

        candidatoEncontrado.getDetalhesIdiomas().remove(idiomaEncontrado);
        idiomaRepository.delete(idiomaEncontrado);
    }

}
