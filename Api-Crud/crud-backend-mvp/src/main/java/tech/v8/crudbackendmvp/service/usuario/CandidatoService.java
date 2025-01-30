package tech.v8.crudbackendmvp.service.usuario;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.v8.crudbackendmvp.exception.ResourceNotFoundException;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.*;
import tech.v8.crudbackendmvp.model.usuario.Candidato;
import tech.v8.crudbackendmvp.model.usuario.Pessoa;
import tech.v8.crudbackendmvp.repository.usuario.CandidatoRepository;

import java.util.List;

import static tech.v8.crudbackendmvp.model.dto.usuario.candidato.CandidatoMapper.toCandidato;
import static tech.v8.crudbackendmvp.model.dto.usuario.candidato.CandidatoMapper.toDTO;

@Service
@AllArgsConstructor
public class CandidatoService {

    private final CandidatoRepository candidatoRepository;
    private final PessoaService pessoaService;

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

    @Transactional
    public CandidatoFrontResposta create(CandidatoFrontCriacao dto) {


        Pessoa novaPessoa = pessoaService.create(dto);

        Candidato novoCandidato = toCandidato(dto, novaPessoa);

        Candidato candidatoSalvo = candidatoRepository.save(novoCandidato);

        novaPessoa.setCandidato(candidatoSalvo);

        return toDTO(candidatoSalvo);
    }

    public CandidatoFrontResposta findDTOById(Long id) {
        return candidatoRepository.findAtivoById(id).map(CandidatoMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Candidado de id " + id + "não encontrado."));
    }

    public Candidato findById(Long id) {
        return candidatoRepository.findAtivoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidado de id " + id + "não encontrado."));
    }

    @Transactional
    public CandidatoFrontResposta update(Long id, CandidatoFrontEdicao dto) {
        Candidato candidatoAntigo = findById(id);

        Candidato candidatoNovo = atualizarAtributos(candidatoAntigo, dto);

        return toDTO(candidatoRepository.save(candidatoNovo));
    }

    private Candidato atualizarAtributos(Candidato candidatoAntigo, CandidatoFrontEdicao dto) {

        Pessoa pessoaEncontrada = pessoaService.findById(candidatoAntigo.getPessoa().getId());

        pessoaEncontrada.setNome(dto.getNome());
        pessoaEncontrada.setCpf(dto.getCpf());
        pessoaEncontrada.setEmail(dto.getEmail());
        pessoaEncontrada.setDataNascimento(dto.getDataNascimento());

        candidatoAntigo.setNomeCompleto(dto.getNomeCompleto());
        candidatoAntigo.setTelefone(dto.getTelefone());
        candidatoAntigo.setPerfilLinkedin(dto.getPerfilLinkedin());
        candidatoAntigo.setGenero(dto.getGenero());
        candidatoAntigo.setEndereco(dto.getEndereco());
        candidatoAntigo.setNumero(dto.getNumero());
        candidatoAntigo.setComplemento(dto.getComplemento());
        candidatoAntigo.setBairro(dto.getBairro());
        candidatoAntigo.setCidade(dto.getCidade());
        candidatoAntigo.setEstado(dto.getEstado());

        return candidatoAntigo;

    }

    public void delete(Long id) {
        candidatoRepository.delete(findById(id));
    }
}
