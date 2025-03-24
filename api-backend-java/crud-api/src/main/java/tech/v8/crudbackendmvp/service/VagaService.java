package tech.v8.crudbackendmvp.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.v8.crudbackendmvp.exception.ResourceNotFoundException;
import tech.v8.crudbackendmvp.model.dto.vaga.*;
import tech.v8.crudbackendmvp.model.dto.vagaaplicada.VagaAplicadaFrontResposta;
import tech.v8.crudbackendmvp.model.dto.vagaaplicada.VagaAplicadaMapper;
import tech.v8.crudbackendmvp.model.enums.Modelo;
import tech.v8.crudbackendmvp.model.enums.StatusVaga;
import tech.v8.crudbackendmvp.model.usuario.Funcionario;
import tech.v8.crudbackendmvp.model.vaga.Vaga;
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
        return VagaMapper.toPage(vagasAtivas, page, size);
    }

    @Transactional
    public VagaFrontResposta create(VagaFrontCriacao dto) {
        try {
            Funcionario funcionarioEncontrado = funcionarioService.findById(dto.getResponsavel_id());

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

    public List<VagaAplicadaFrontResposta> findVagasAplicadasDTOById(Long id) {
        return vagaRepository.findAllVagasAplicadasByVagaId(id).stream()
                .map(VagaAplicadaMapper::toDTO).toList();
    }

    public Vaga findById(Long id) {
        return vagaRepository.findAtivoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vaga de id " + id + " não encontrada."));
    }

    public VagaPage search(String nome, String modelo, String local, String status, @PositiveOrZero int page, @Positive int size){

        // Validação: deve ser informado pelo menos um parâmetro de filtro.
        if (    (nome == null || nome.isBlank()) &&
                (modelo == null || modelo.isBlank()) &&
                (local == null || local.isBlank()) &&
                (status == null || status.isBlank())

        ) {
            throw new IllegalArgumentException("Ao menos um parâmetro de busca deve ser informado (nome, modelo, local ou status).");
        }


        String modeloParametro = null;
        String statusParametro = null;

        if (modelo != null && !modelo.isBlank()) {
            modeloParametro = Modelo.fromString(modelo).name();
        }
        if (status != null && !status.isBlank()) {
            statusParametro = StatusVaga.fromString(status).name();
        }

        List<Vaga> vagas = vagaRepository.findAllAtivosByFilters(nome, modeloParametro, local, statusParametro);
        return VagaMapper.toPage(vagas, page, size);
    }

    public Vaga getVagaReferenceById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID da vaga não pode ser nulo");
        }
        return findById(id);

    }

    @Transactional
    public VagaFrontResposta update(Long id, VagaFrontEdicao dto) {
        Vaga vagaAntiga = findById(id);

        Vaga vagaNova = atualizarAtributos(vagaAntiga, dto);
        return toDTO(vagaRepository.save(vagaNova));

    }

    private Vaga atualizarAtributos(Vaga vagaAntiga, VagaFrontEdicao dto) {

        if (dto.getResponsavel_id() != null){
            Funcionario funcionario = funcionarioService.findById(dto.getResponsavel_id());
            vagaAntiga.setResponsavel(funcionario);
        }

        vagaAntiga.setNome(dto.getNome());
        vagaAntiga.setTipo(dto.getTipo());
        vagaAntiga.setLocalidade(dto.getLocalidade());
        vagaAntiga.setDescricao(dto.getDescricao());
        vagaAntiga.setResponsabilidades(dto.getResponsabilidades());
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


    @Transactional
    public void delete(Long id) {
        Vaga vagaEncontrada = findById(id);
        vagaEncontrada.setEstadoLogico(false);
        vagaRepository.save(vagaEncontrada);
    }
}

