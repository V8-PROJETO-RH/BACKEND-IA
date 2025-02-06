package tech.v8.crudbackendmvp.service;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.v8.crudbackendmvp.exception.ResourceNotFoundException;
import tech.v8.crudbackendmvp.model.dto.vagaaplicada.*;
import tech.v8.crudbackendmvp.model.usuario.Candidato;
import tech.v8.crudbackendmvp.model.vaga.Vaga;
import tech.v8.crudbackendmvp.model.vaga.VagaAplicada;
import tech.v8.crudbackendmvp.repository.VagaAplicadaRepository;
import tech.v8.crudbackendmvp.service.usuario.CandidatoService;

import java.util.List;

import static tech.v8.crudbackendmvp.model.dto.vagaaplicada.VagaAplicadaMapper.toDTO;
import static tech.v8.crudbackendmvp.model.dto.vagaaplicada.VagaAplicadaMapper.toVagaAplicada;

@Service
@AllArgsConstructor
public class VagaAplicadaService {

    private final VagaAplicadaRepository vagaAplicadaRepository;
    private final VagaService vagaService;
    private final CandidatoService candidatoService;

    public VagaAplicadaPage list(@PositiveOrZero int page, @Positive @Max(50) int size) {
        List<VagaAplicada> vagasAtivas = vagaAplicadaRepository.findAll();
        List<VagaAplicadaFrontResposta> vagas = vagasAtivas.stream()
                .skip((long) page * size)
                .limit(size)
                .map(VagaAplicadaMapper::toDTO)
                .toList();
        int totalElements = vagasAtivas.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        return new VagaAplicadaPage(vagas, totalPages, totalElements);
    }

    @Transactional
    public VagaAplicadaFrontResposta create(VagaAplicadaFrontCriacao dto) {
        try {
            Candidato candidatoEncontrado = candidatoService.findById(dto.getCandidatoId());
            Vaga vagaEncontrada = vagaService.getVagaReferenceById(dto.getVagaId());

            VagaAplicada novaVagaAplicada = toVagaAplicada(dto, candidatoEncontrado, vagaEncontrada);

            VagaAplicada vagaAplicadaSalva = vagaAplicadaRepository.save(novaVagaAplicada);


            candidatoEncontrado.getVagasAplicadas().add(vagaAplicadaSalva);
            vagaEncontrada.getVagasAplicadas().add(vagaAplicadaSalva);

            return toDTO(vagaAplicadaSalva);
        }catch(EntityNotFoundException e){
            throw new ResourceNotFoundException("Erro." + e.getMessage());
        }
    }

    public VagaAplicadaFrontResposta findDTOById(Long id) {
        return vagaAplicadaRepository.findById(id).map(VagaAplicadaMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Vaga aplicada de id " + id + " não encontrada."));
    }

    public VagaAplicada findById(Long id) {
        return vagaAplicadaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vaga aplicada de id " + id + " não encontrada."));
    }

    public VagaAplicada getVagaAplicadaReferenceById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID da vaga aplicada não pode ser nulo");
        }
        return vagaAplicadaRepository.getReferenceById(id);
    }

    @Transactional
    public VagaAplicadaFrontResposta update(Long id, VagaAplicadaFrontEdicao dto) {
        VagaAplicada vagaAplicadaAntiga = findById(id);

        VagaAplicada vagaAplicadaNova = atualizarAtributos(vagaAplicadaAntiga, dto);

        return toDTO(vagaAplicadaRepository.save(vagaAplicadaNova));
    }

    private VagaAplicada atualizarAtributos(VagaAplicada vagaAplicadaAntiga, VagaAplicadaFrontEdicao dto) {

        if (!dto.getCandidatoId().equals(vagaAplicadaAntiga.getCandidato().getId())) {
            Candidato candidatoEncontrado = candidatoService.findById(dto.getCandidatoId());
            vagaAplicadaAntiga.setCandidato(candidatoEncontrado);

        }
        if (!dto.getVagaId().equals(vagaAplicadaAntiga.getVaga().getId())) {

            Vaga vagaEncontrada = vagaService.getVagaReferenceById(dto.getVagaId());
            vagaAplicadaAntiga.setVaga(vagaEncontrada);
        }

        vagaAplicadaAntiga.setNomeIndicacao(dto.getNomeIndicacao());
        vagaAplicadaAntiga.setStatus(dto.getStatus());

        return vagaAplicadaAntiga;
    }


    public void delete(Long id) {
        VagaAplicada vagaAplicada = findById(id);
        vagaAplicadaRepository.delete(vagaAplicada);
    }
}
