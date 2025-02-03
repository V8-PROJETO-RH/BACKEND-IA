package tech.v8.crudbackendmvp.service.usuario;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.v8.crudbackendmvp.exception.ResourceNotFoundException;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.certificados.CertificadoFrontCriacao;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.certificados.CertificadoFrontResposta;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.certificados.CertificadoMapper;
import tech.v8.crudbackendmvp.model.usuario.Candidato;
import tech.v8.crudbackendmvp.model.usuario.Certificado;
import tech.v8.crudbackendmvp.repository.usuario.CertificadoRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CertificadoService {

    private final CandidatoService candidatoService;
    private final CertificadoRepository certificadoRepository;

    public List<CertificadoFrontResposta> findCertificadosByCandidatoId(Long candidatoId) {
        return certificadoRepository.findAllByCandidatoId(candidatoId).stream()
                .map(CertificadoMapper::toDTO)
                .toList();
    }

    public Certificado getCertificadoReferenceById(Long id) {
        return certificadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Certificado de id " + id + " não encontrado."));
    }

    public CertificadoFrontResposta create(Long idCandidato, CertificadoFrontCriacao certificado) {
        Candidato candidatoEncontrado = candidatoService.getCandidatoReferenceById(idCandidato);
        Certificado certificadoNovo = CertificadoMapper.toCertificado(certificado, candidatoEncontrado);
        Certificado certificadoSalvo = certificadoRepository.save(certificadoNovo);

        candidatoEncontrado.getCertificados().add(certificadoSalvo);

        return CertificadoMapper.toDTO(certificadoSalvo);

    }

    public CertificadoFrontResposta update(Long idCertificado, CertificadoFrontCriacao certificado) {
        Certificado certificadoEncontrado = getCertificadoReferenceById(idCertificado);

        certificadoEncontrado.setNome(certificado.getNome());
        certificadoEncontrado.setDataEmissao(certificado.getDtEmissao());
        certificadoEncontrado.setDataVencimento(certificado.getDtVencimento());

        Certificado certificadoSalvo = certificadoRepository.save(certificadoEncontrado);
        return CertificadoMapper.toDTO(certificadoSalvo);
    }

    public void delete(Long idCandidato, Long idCertificado) {
        Candidato candidatoEncontrado = candidatoService.getCandidatoReferenceById(idCandidato);
        Certificado certificadoEncontrado = getCertificadoReferenceById(idCertificado);

        candidatoEncontrado.getCertificados().remove(certificadoEncontrado);
        certificadoRepository.delete(certificadoEncontrado);
    }
}
