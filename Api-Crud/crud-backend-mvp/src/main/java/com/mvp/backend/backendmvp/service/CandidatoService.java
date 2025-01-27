package com.mvp.backend.backendmvp.service;


import com.mvp.backend.backendmvp.exception.ResourceNotFoundException;
import com.mvp.backend.backendmvp.model.Candidato;
import com.mvp.backend.backendmvp.model.dto.candidato.*;
import com.mvp.backend.backendmvp.repository.CandidatoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mvp.backend.backendmvp.model.dto.candidato.CandidatoMapper.toDTO;
import static com.mvp.backend.backendmvp.model.dto.candidato.CandidatoMapper.toCandidato;

@Service
@AllArgsConstructor
public class CandidatoService {

    private final CandidatoRepository candidatoRepository;

    public CandidatoPageDTO list(@PositiveOrZero int page, @Positive @Max(50) int size) {
            List<Candidato> candidatosAtivos = candidatoRepository.findAllAtivos();
            List<CandidatoFrontDTOResposta> candidatos = candidatosAtivos.stream()
                    .skip((long) page * size)
                    .limit(size)
                    .map(CandidatoMapper::toDTO)
                    .toList();
            int totalElements = candidatosAtivos.size();
            int totalPages = (int) Math.ceil((double) totalElements / size);
            return new CandidatoPageDTO(candidatos, totalPages, totalElements);
    }

    public CandidatoFrontDTOResposta create(CandidatoFrontDTOCriacao dto) {
        return toDTO(candidatoRepository.save(toCandidato(dto)));
    }

    public CandidatoFrontDTOResposta findById(Long id) {
        return candidatoRepository.findAtivoById(id).map(CandidatoMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Candidado de id " + id + "não encontrado."));
    }

    @Transactional
    public CandidatoFrontDTOResposta update(Long id, CandidatoFrontDTOEdicao dto) {
        Candidato candidatoAntigo = candidatoRepository.findAtivoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidado de id " + id + "não encontrado."));

        Candidato candidatoNovo = atualizarAtributos(candidatoAntigo, dto);
        return toDTO(candidatoRepository.save(candidatoNovo));
    }

    private Candidato atualizarAtributos(Candidato candidatoAntigo, CandidatoFrontDTOEdicao dto) {

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
