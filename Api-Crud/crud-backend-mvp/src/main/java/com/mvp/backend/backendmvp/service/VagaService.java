package com.mvp.backend.backendmvp.service;

import com.mvp.backend.backendmvp.exception.ResourceNotFoundException;
import com.mvp.backend.backendmvp.model.Funcionario;
import com.mvp.backend.backendmvp.model.Vaga;
import com.mvp.backend.backendmvp.model.dto.vaga.*;
import com.mvp.backend.backendmvp.model.enums.StatusVaga;
import com.mvp.backend.backendmvp.repository.FuncionarioRepository;
import com.mvp.backend.backendmvp.repository.VagaRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mvp.backend.backendmvp.model.dto.vaga.VagaMapper.toDTO;
import static com.mvp.backend.backendmvp.model.dto.vaga.VagaMapper.toVaga;

@Service
@AllArgsConstructor
public class VagaService {

    private VagaRepository vagaRepository;

    private FuncionarioRepository funcionarioRepository;

    public List<VagaFrontDTOResposta> list() {
        return vagaRepository.findAllAtivos().stream().map(VagaMapper::toDTO).toList();
    }

    public VagaFrontDTOResposta create(VagaFrontDTOCriacao dto) {
        return toDTO(vagaRepository.save(toVaga(dto)));
    }

    public VagaFrontDTOResposta findById(Long id) {
       return vagaRepository.findAtivoById(id).map(VagaMapper::toDTO)
               .orElseThrow(() -> new ResourceNotFoundException("Vaga de id " + id + " não encontrada."));
    }

    @Transactional
    public VagaFrontDTOResposta update(Long id, VagaFrontDTOEdicao dto) {
        Vaga vagaAntiga = vagaRepository.findAtivoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vaga de id " + id + " não encontrada."));

            Vaga vagaNova = atualizarAtributos(vagaAntiga, dto);
            return toDTO(vagaRepository.save(vagaNova));

    }

    private Vaga atualizarAtributos(Vaga vagaAntiga, VagaFrontDTOEdicao dto) {

        if (dto.getFuncionario_id() != null){
            Funcionario funcionario = funcionarioRepository.findById(dto.getFuncionario_id())
                    .orElseThrow(() -> new ResourceNotFoundException("Funcionário de id " + dto.getFuncionario_id() + " não encontrado."));

            vagaAntiga.setFuncionario(funcionario);
        }


        vagaAntiga.setNome(dto.getNome());
        vagaAntiga.setSalario(dto.getSalario());
        vagaAntiga.setQtd(dto.getQuantidade());
        vagaAntiga.setDescricao(dto.getDescricao());
        vagaAntiga.setResponsavel(dto.getResponsavel());
        vagaAntiga.setContratacao(dto.getContratacao());
        vagaAntiga.setStatus(StatusVaga.valueOf(dto.getStatus()));
        vagaAntiga.setRequisitos(dto.getRequisitos());
        vagaAntiga.setModelo(dto.getModelo());
        vagaAntiga.setAtribuicoes(dto.getAtribuicoes());
        vagaAntiga.setBeneficios(dto.getBeneficios());

        return vagaAntiga;
    }


    public void delete(Long id) {
        vagaRepository.delete(vagaRepository.findAtivoById(id).orElseThrow(() -> new ResourceNotFoundException("Erro ao deletar a vaga de id  " + id + ".")));
    }
}
