package tech.v8.crudbackendmvp.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.v8.crudbackendmvp.exception.ResourceNotFoundException;
import tech.v8.crudbackendmvp.model.usuario.Funcionario;
import tech.v8.crudbackendmvp.model.vaga.Vaga;
import tech.v8.crudbackendmvp.model.dto.vaga.*;
import tech.v8.crudbackendmvp.repository.VagaRepository;
import tech.v8.crudbackendmvp.service.usuario.FuncionarioService;

import java.util.List;

import static tech.v8.crudbackendmvp.model.dto.vaga.VagaMapper.toDTO;
import static tech.v8.crudbackendmvp.model.dto.vaga.VagaMapper.toVaga;

@Service
@AllArgsConstructor
public class VagaService {

    private VagaRepository vagaRepository;

    private FuncionarioService funcionarioService;

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

    @Transactional
    public VagaFrontResposta create(VagaFrontCriacao dto) {
        try {
            Funcionario funcionarioEncontrado = funcionarioService.getFuncionarioReferenceById(dto.getResponsavel_id());

            Vaga novaVaga = toVaga(dto);
            novaVaga.setResponsavel(funcionarioEncontrado);

            Vaga vagaSalva = vagaRepository.save(novaVaga);


            funcionarioEncontrado.getVagas().add(vagaSalva);

            return toDTO(vagaSalva);
        }catch(EntityNotFoundException e){
            throw new ResourceNotFoundException("Funcionário com ID " + dto.getResponsavel_id() + " não encontrado.");
        }
    }

    public VagaFrontResposta findDTOById(Long id) {
        return vagaRepository.findAtivoById(id).map(VagaMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Vaga de id " + id + " não encontrada."));
    }

    public Vaga findById(Long id) {
        return vagaRepository.findAtivoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vaga de id " + id + " não encontrada."));
    }

    @Transactional
    public VagaFrontResposta update(Long id, VagaFrontEdicao dto) {
        Vaga vagaAntiga = findById(id);

        Vaga vagaNova = atualizarAtributos(vagaAntiga, dto);
        return toDTO(vagaRepository.save(vagaNova));

    }

    private Vaga atualizarAtributos(Vaga vagaAntiga, VagaFrontEdicao dto) {

        if (dto.getResponsavel_id() != null){
            Funcionario funcionario = funcionarioService.getFuncionarioReferenceById(dto.getResponsavel_id());
            vagaAntiga.setResponsavel(funcionario);
        }

        vagaAntiga.setProvas(dto.getProvas());

        vagaAntiga.setNome(dto.getNome());
        vagaAntiga.setTipo(dto.getTipo());
        vagaAntiga.setLocalidade(dto.getLocalidade());
        vagaAntiga.setDescricao(dto.getDescricao());
        vagaAntiga.setResponsabilidade(dto.getResponsabilidade());
        vagaAntiga.setRequisitos(dto.getRequisitos());
        vagaAntiga.setFaixaSalarial(dto.getFaixaSalarial());
        vagaAntiga.setRegimeContratacao(dto.getRegimeContratacao());
        vagaAntiga.setBeneficios(dto.getBeneficios());

        vagaAntiga.setModelo(dto.getModelo());
        vagaAntiga.setStatus(dto.getStatus());

        vagaAntiga.setQtdVagas(dto.getQuantidade());
        vagaAntiga.setAtribuicoes(dto.getAtribuicoes());


        return vagaAntiga;
    }


    public void delete(Long id) {
        vagaRepository.delete(vagaRepository.findAtivoById(id).orElseThrow(() -> new ResourceNotFoundException("Erro ao deletar a vaga de id  " + id + ".")));
    }
}

