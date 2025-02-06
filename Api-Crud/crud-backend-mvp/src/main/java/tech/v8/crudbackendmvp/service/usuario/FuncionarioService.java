package tech.v8.crudbackendmvp.service.usuario;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.v8.crudbackendmvp.exception.ResourceNotFoundException;
import tech.v8.crudbackendmvp.model.enums.Role;
import tech.v8.crudbackendmvp.model.usuario.Funcionario;
import tech.v8.crudbackendmvp.model.dto.usuario.funcionario.FuncionarioFrontCriacao;
import tech.v8.crudbackendmvp.model.dto.usuario.funcionario.FuncionarioFrontEdicao;
import tech.v8.crudbackendmvp.model.dto.usuario.funcionario.FuncionarioFrontResposta;
import tech.v8.crudbackendmvp.model.dto.usuario.funcionario.FuncionarioMapper;
import tech.v8.crudbackendmvp.model.usuario.Pessoa;
import tech.v8.crudbackendmvp.repository.usuario.FuncionarioRepository;

import java.util.List;

import static tech.v8.crudbackendmvp.model.dto.usuario.funcionario.FuncionarioMapper.toDTO;
import static tech.v8.crudbackendmvp.model.dto.usuario.funcionario.FuncionarioMapper.toFuncionario;

@Service
@AllArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final PessoaService pessoaService;

    public List<FuncionarioFrontResposta> list() {
        return funcionarioRepository.findAllAtivos().stream().map(FuncionarioMapper::toDTO).toList();

    }

    @Transactional
    public FuncionarioFrontResposta create(FuncionarioFrontCriacao dto) {
        Pessoa novaPessoa = pessoaService.create(dto, Role.RH.name());

        Funcionario novoFuncionario = toFuncionario(dto, novaPessoa);

        Funcionario funcionarioSalvo = funcionarioRepository.save(novoFuncionario);

        novaPessoa.setFuncionario(funcionarioSalvo);

        return toDTO(funcionarioSalvo);
    }

    public FuncionarioFrontResposta findDTOById(Long id) {
        return funcionarioRepository.findAtivoById(id).map(FuncionarioMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário de id " + id + " não encontrado."));
    }

    public Funcionario findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do funcionário não pode ser nulo");
        }

        return funcionarioRepository.findAtivoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário de id " + id + " não encontrado."));
    }

    public Funcionario getFuncionarioReferenceById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do funcionário não pode ser nulo");
        }
        return funcionarioRepository.getReferenceById(id);

    }

    @Transactional
    public FuncionarioFrontResposta update(Long id, FuncionarioFrontEdicao dto) {
        Funcionario funcionarioAntigo = findById(id);

        Funcionario funcionarioNovo = atualizarAtributos(funcionarioAntigo, dto);
        return toDTO(funcionarioRepository.save(funcionarioNovo));
    }

    private Funcionario atualizarAtributos(Funcionario funcionarioAntigo, FuncionarioFrontEdicao dto) {

        Pessoa pessoaEcontrada = pessoaService.findById(funcionarioAntigo.getPessoa().getId());

        pessoaEcontrada.setNome(dto.getNome());
        pessoaEcontrada.setEmail(dto.getEmail());
        pessoaEcontrada.setCpf(dto.getCpf());
        pessoaEcontrada.setDataNascimento(dto.getDataNascimento());

        funcionarioAntigo.setDepartamento(dto.getDepartamento());

        return funcionarioAntigo;
    }

    public void delete(Long id){
        funcionarioRepository.delete(findById(id));
    }

}
