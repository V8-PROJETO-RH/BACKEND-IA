package tech.v8.crudbackendmvp.service.usuario;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.v8.crudbackendmvp.exception.ResourceNotFoundException;
import tech.v8.crudbackendmvp.model.dto.usuario.pessoa.PessoaFrontCriacao;
import tech.v8.crudbackendmvp.model.usuario.Pessoa;
import tech.v8.crudbackendmvp.repository.usuario.PessoaRepository;

import static tech.v8.crudbackendmvp.model.dto.usuario.pessoa.PessoaMapper.toPessoa;

@Service
@AllArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    @Transactional
    public Pessoa create(PessoaFrontCriacao dto) {

        Pessoa novaPessoa = toPessoa(dto);

        return pessoaRepository.save(novaPessoa);
    }

    public Pessoa findById(Long id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa de id " + id + " não encontrada."));
    }
}
