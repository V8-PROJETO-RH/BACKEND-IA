package tech.v8.crudbackendmvp.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.v8.crudbackendmvp.model.dto.resultado.ResultadoFinalMapper;
import tech.v8.crudbackendmvp.model.dto.resultado.ResultadoFrontCriacao;
import tech.v8.crudbackendmvp.model.dto.resultado.ResultadoFrontResposta;
import tech.v8.crudbackendmvp.model.vaga.Prova;
import tech.v8.crudbackendmvp.model.vaga.ResultadoFinal;
import tech.v8.crudbackendmvp.model.vaga.VagaAplicada;
import tech.v8.crudbackendmvp.repository.ResultadoFinalRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ResultadoFinalService {

    private ResultadoFinalRepository repository;
    private VagaAplicadaService vagaAplicadaService;
    private ProvaService provaService;



    public List<ResultadoFrontResposta> findResultadosById(Long id) {
        return repository.findAllByVagaId(id).stream()
                .map(ResultadoFinalMapper::toDTO).toList();
    }

    public ResultadoFrontResposta createResultado(Long idVagaAplicada, ResultadoFrontCriacao dto) {
        VagaAplicada vagaAplicadaEncontrada = vagaAplicadaService.findById(idVagaAplicada);
        Prova provaEncontrada = provaService.findById(dto.getProvaId());
        ResultadoFinal novoResultado = ResultadoFinalMapper.toResultadoFinal(dto, vagaAplicadaEncontrada, provaEncontrada);

        ResultadoFinal resultadoSalvo = repository.save(novoResultado);

        vagaAplicadaEncontrada.setResultadoFinal(resultadoSalvo);
        provaEncontrada.getResultados().add(resultadoSalvo);


        return ResultadoFinalMapper.toDTO(resultadoSalvo);

    }
}
