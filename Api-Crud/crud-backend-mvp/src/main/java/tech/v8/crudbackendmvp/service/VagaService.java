package tech.v8.crudbackendmvp.service;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.v8.crudbackendmvp.exception.ResourceNotFoundException;
import tech.v8.crudbackendmvp.model.Funcionario;
import tech.v8.crudbackendmvp.model.Vaga;
import tech.v8.crudbackendmvp.model.dto.vaga.*;
import tech.v8.crudbackendmvp.model.enums.StatusVaga;
import tech.v8.crudbackendmvp.repository.FuncionarioRepository;
import tech.v8.crudbackendmvp.repository.VagaRepository;

import java.util.List;

import static tech.v8.crudbackendmvp.model.dto.vaga.VagaMapper.toDTO;
import static tech.v8.crudbackendmvp.model.dto.vaga.VagaMapper.toVaga;

@Service
@AllArgsConstructor
public class VagaService {

    private VagaRepository vagaRepository;

    private FuncionarioRepository funcionarioRepository;

    public VagaPage list(@PositiveOrZero int page, @Positive @Max(50) int size) {
        List<Vaga> vagasAtivas = vagaRepository.findAllAtivos();
        List<VagaFrontResposta> vagas = vagasAtivas.stream()
                .skip((long) page * size)
                .limit(size)
                .map(VagaMapper::toDTO)
                .toList();
        int totalElements = vagasAtivas.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        return new VagaPage(vagas, totalPages, totalElements);
    }

    public VagaFrontResposta create(VagaFrontCriacao dto) {
        return toDTO(vagaRepository.save(toVaga(dto)));
    }

    public VagaFrontResposta findById(Long id) {
        return vagaRepository.findAtivoById(id).map(VagaMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Vaga de id " + id + " não encontrada."));
    }

    @Transactional
    public VagaFrontResposta update(Long id, VagaFrontEdicao dto) {
        Vaga vagaAntiga = vagaRepository.findAtivoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vaga de id " + id + " não encontrada."));

        Vaga vagaNova = atualizarAtributos(vagaAntiga, dto);
        return toDTO(vagaRepository.save(vagaNova));

    }

    private Vaga atualizarAtributos(Vaga vagaAntiga, VagaFrontEdicao dto) {

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

