package tech.v8.crudbackendmvp.service;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.v8.crudbackendmvp.exception.ResourceNotFoundException;
import tech.v8.crudbackendmvp.model.Funcionario;
import tech.v8.crudbackendmvp.model.dto.funcionario.FuncionarioFrontCriacao;
import tech.v8.crudbackendmvp.model.dto.funcionario.FuncionarioFrontEdicao;
import tech.v8.crudbackendmvp.model.dto.funcionario.FuncionarioFrontResposta;
import tech.v8.crudbackendmvp.model.dto.funcionario.FuncionarioMapper;
import tech.v8.crudbackendmvp.repository.FuncionarioRepository;

import java.util.List;

import static tech.v8.crudbackendmvp.model.dto.funcionario.FuncionarioMapper.toDTO;
import static tech.v8.crudbackendmvp.model.dto.funcionario.FuncionarioMapper.toFuncionario;

@Service
@AllArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    public List<FuncionarioFrontResposta> list() {
        return funcionarioRepository.findAllAtivos().stream().map(FuncionarioMapper::toDTO).toList();

    }

    public FuncionarioFrontResposta create(FuncionarioFrontCriacao dto) {
        return toDTO(funcionarioRepository.save(toFuncionario(dto)));
    }

    public FuncionarioFrontResposta findById(Long id) {
        return funcionarioRepository.findAtivoById(id).map(FuncionarioMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário de id " + id + " não encontrado."));
    }

    @Transactional
    public FuncionarioFrontResposta update(Long id, FuncionarioFrontEdicao dto) {
        Funcionario funcionarioAntigo = funcionarioRepository.findAtivoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário com id " + id + " não encontrado."));


        Funcionario funcionarioNovo = atualizarAtributos(funcionarioAntigo, dto);
        return toDTO(funcionarioRepository.save(funcionarioNovo));
    }

    private Funcionario atualizarAtributos(Funcionario funcionarioAntigo, FuncionarioFrontEdicao dto) {

        funcionarioAntigo.setNome(dto.getNome());
        funcionarioAntigo.setDepartamento(dto.getDepartamento());
        funcionarioAntigo.setEmail(dto.getEmail());
        funcionarioAntigo.setTelefone(dto.getTelefone());

        return funcionarioAntigo;
    }

    public void delete(Long id){
        funcionarioRepository.delete(funcionarioRepository.findAtivoById(id).orElseThrow(() -> new ResourceNotFoundException("Erro ao deletar o funcionário de id " + id + ".")));
    }

}
