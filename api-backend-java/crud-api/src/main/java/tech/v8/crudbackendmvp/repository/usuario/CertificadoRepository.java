package tech.v8.crudbackendmvp.repository.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.v8.crudbackendmvp.model.usuario.Certificado;

import java.util.List;

public interface CertificadoRepository extends JpaRepository<Certificado, Long> {
    List<Certificado> findAllByCandidatoId(Long candidatoId);
}
