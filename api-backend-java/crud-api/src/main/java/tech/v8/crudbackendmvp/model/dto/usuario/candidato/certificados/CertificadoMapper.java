package tech.v8.crudbackendmvp.model.dto.usuario.candidato.certificados;

import org.springframework.stereotype.Component;
import tech.v8.crudbackendmvp.model.usuario.Candidato;
import tech.v8.crudbackendmvp.model.usuario.Certificado;

@Component
public class CertificadoMapper {
    public static CertificadoFrontResposta toDTO(Certificado certificado) {
        if (certificado == null) return null;

        return new CertificadoFrontResposta(certificado);
    }

    public static Certificado toCertificado(CertificadoFrontCriacao certificado, Candidato candidato) {
        return new Certificado(certificado, candidato);
    }
}
