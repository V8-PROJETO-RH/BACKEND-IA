package com.mvp.backend.backendmvp.service;

import com.mvp.backend.backendmvp.exception.ResourceNotFoundException;
import com.mvp.backend.backendmvp.model.Funcionario;
import com.mvp.backend.backendmvp.model.dto.funcionario.*;
import com.mvp.backend.backendmvp.repository.FuncionarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mvp.backend.backendmvp.model.dto.funcionario.FuncionarioMapper.toDTO;
import static com.mvp.backend.backendmvp.model.dto.funcionario.FuncionarioMapper.toFuncionario;

@Service
@AllArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    public List<FuncionarioFrontDTOResposta> list() {
        return funcionarioRepository.findAllAtivos().stream().map(FuncionarioMapper::toDTO).toList();

    }

    public FuncionarioFrontDTOResposta create(FuncionarioFrontDTOCriacao dto) {
        return toDTO(funcionarioRepository.save(toFuncionario(dto)));
    }

    public FuncionarioFrontDTOResposta findById(Long id) {
        return funcionarioRepository.findAtivoById(id).map(FuncionarioMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário de id " + id + " não encontrado."));
    }

    @Transactional
    public FuncionarioFrontDTOResposta update(Long id, FuncionarioFrontDTOEdicao dto) {
        Funcionario funcionarioAntigo = funcionarioRepository.findAtivoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário com id " + id + " não encontrado."));


        Funcionario funcionarioNovo = atualizarAtributos(funcionarioAntigo, dto);
        return toDTO(funcionarioRepository.save(funcionarioNovo));
    }

    private Funcionario atualizarAtributos(Funcionario funcionarioAntigo, FuncionarioFrontDTOEdicao dto) {

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
