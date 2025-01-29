package tech.v8.crudbackendmvp.service;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.v8.crudbackendmvp.exception.ResourceNotFoundException;
import tech.v8.crudbackendmvp.model.Candidato;
import tech.v8.crudbackendmvp.model.dto.candidato.*;
import tech.v8.crudbackendmvp.repository.CandidatoRepository;

import java.util.List;

import static tech.v8.crudbackendmvp.model.dto.candidato.CandidatoMapper.toCandidato;
import static tech.v8.crudbackendmvp.model.dto.candidato.CandidatoMapper.toDTO;

@Service
@AllArgsConstructor
public class CandidatoService {

    private final CandidatoRepository candidatoRepository;

    public CandidatoPage list(@PositiveOrZero int page, @Positive @Max(50) int size) {
        List<Candidato> candidatosAtivos = candidatoRepository.findAllAtivos();
        List<CandidatoFrontResposta> candidatos = candidatosAtivos.stream()
                .skip((long) page * size)
                .limit(size)
                .map(CandidatoMapper::toDTO)
                .toList();
        int totalElements = candidatosAtivos.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        return new CandidatoPage(candidatos, totalPages, totalElements);
    }

    public CandidatoFrontResposta create(CandidatoFrontCriacao dto) {
        return toDTO(candidatoRepository.save(toCandidato(dto)));
    }

    public CandidatoFrontResposta findById(Long id) {
        return candidatoRepository.findAtivoById(id).map(CandidatoMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Candidado de id " + id + "não encontrado."));
    }

    @Transactional
    public CandidatoFrontResposta update(Long id, CandidatoFrontEdicao dto) {
        Candidato candidatoAntigo = candidatoRepository.findAtivoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidado de id " + id + "não encontrado."));

        Candidato candidatoNovo = atualizarAtributos(candidatoAntigo, dto);
        return toDTO(candidatoRepository.save(candidatoNovo));
    }

    private Candidato atualizarAtributos(Candidato candidatoAntigo, CandidatoFrontEdicao dto) {

        candidatoAntigo.setNome(dto.getNome());
        candidatoAntigo.setCpf(dto.getCpf());
        candidatoAntigo.setEmail(dto.getEmail());
        candidatoAntigo.setTelefone(dto.getTelefone());
        candidatoAntigo.setLinkedinProfile(dto.getLinkedin_profile());
        candidatoAntigo.setDataNascimento(dto.getData_nascimento());

        return candidatoAntigo;

    }

    public void delete(Long id) {
        candidatoRepository.delete(candidatoRepository.findAtivoById(id).orElseThrow(() -> new ResourceNotFoundException("Erro ao deletar o candidato de id " + id + ".")));
    }
}
