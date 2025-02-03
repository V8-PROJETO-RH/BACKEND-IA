package tech.v8.crudbackendmvp.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.v8.crudbackendmvp.exception.ResourceNotFoundException;
import tech.v8.crudbackendmvp.model.dto.prova.ProvaFrontCriacao;
import tech.v8.crudbackendmvp.model.dto.prova.ProvaFrontResposta;
import tech.v8.crudbackendmvp.model.dto.prova.ProvaMapper;
import tech.v8.crudbackendmvp.model.vaga.Prova;
import tech.v8.crudbackendmvp.model.vaga.Vaga;
import tech.v8.crudbackendmvp.repository.ProvaRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ProvaService {
    private ProvaRepository provaRepository;
    private VagaService vagaService;


    public Prova findById(Long id) {
        return provaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prova de id " + id + " não encontrada."));
    }

    public List<ProvaFrontResposta> findProvasDTOByVagaId(Long id) {
        return provaRepository.findAllByVagaId(id).stream()
                .map(ProvaMapper::toDTO)
                .toList();
    }

    public ProvaFrontResposta create(Long id, ProvaFrontCriacao prova) {
        Vaga vagaEcontrada = vagaService.findById(id);

        Prova novaProva = ProvaMapper.toProva(prova, vagaEcontrada);
        Prova provaSalva =  provaRepository.save(novaProva);

        vagaEcontrada.getProvas().add(provaSalva);

        return ProvaMapper.toDTO(provaSalva);
    }

    public ProvaFrontResposta update(Long id, ProvaFrontCriacao prova) {
        Prova provaEncontrada = findById(id);
        provaEncontrada.setDescricao(prova.getDescricao());
        Prova provaAtualizada = provaRepository.save(provaEncontrada);
        return ProvaMapper.toDTO(provaAtualizada);
    }

    public void delete(Long id) {
        Prova provaEncontrada = findById(id);
        provaRepository.delete(provaEncontrada);
    }
}
