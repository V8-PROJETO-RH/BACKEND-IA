package tech.v8.crudbackendmvp.service.usuario;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.v8.crudbackendmvp.exception.ResourceNotFoundException;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.formacoes.FormacaoFrontCriacao;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.formacoes.FormacaoFrontResposta;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.formacoes.FormacaoMapper;
import tech.v8.crudbackendmvp.model.usuario.Candidato;
import tech.v8.crudbackendmvp.model.usuario.DetalhesFormacao;
import tech.v8.crudbackendmvp.repository.usuario.DetalhesFormacaoRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class FormacaoService {
    private final DetalhesFormacaoRepository formacaoRepository;
    private final CandidatoService candidatoService;

    public List<FormacaoFrontResposta> findFormacoesByCandidatoId(Long candidatoId) {
        return formacaoRepository.findAllByCandidatoId(candidatoId).stream()
                .map(FormacaoMapper::toDTO)
                .toList();
    }

    public DetalhesFormacao getFormacaoReferenceById(Long id) {
        return formacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Formação de id " + id + " não encontrada."));
    }

    public FormacaoFrontResposta create(Long idCandidato, FormacaoFrontCriacao formacao) {
        Candidato candidatoEncontrado = candidatoService.getCandidatoReferenceById(idCandidato);
        DetalhesFormacao formacaoNova = FormacaoMapper.toFormacao(formacao, candidatoEncontrado);
        DetalhesFormacao formacaoSalva = formacaoRepository.save(formacaoNova);

        candidatoEncontrado.getDetalhesFormacao().add(formacaoSalva);

        return FormacaoMapper.toDTO(formacaoSalva);

    }

    public FormacaoFrontResposta update(Long idFormacao, FormacaoFrontCriacao formacao) {
        DetalhesFormacao formacaoEncontrada = getFormacaoReferenceById(idFormacao);

        formacaoEncontrada.setNomeInstituicao(formacao.getNomeInstituicao());
        formacaoEncontrada.setEscolaridade(formacao.getEscolaridade());
        formacaoEncontrada.setArea(formacao.getArea());
        formacaoEncontrada.setDataInicio(formacao.getDtInicio());
        formacaoEncontrada.setDataFinal(formacao.getDtFinal());

        DetalhesFormacao formacaoSalva = formacaoRepository.save(formacaoEncontrada);
        return FormacaoMapper.toDTO(formacaoSalva);
    }

    public void delete(Long idCandidato, Long idFormacao) {
        Candidato candidatoEncontrado = candidatoService.getCandidatoReferenceById(idCandidato);
        DetalhesFormacao formacaoEncontrada = getFormacaoReferenceById(idFormacao);

        candidatoEncontrado.getDetalhesFormacao().remove(formacaoEncontrada);
        formacaoRepository.delete(formacaoEncontrada);
    }
}
