package tech.v8.crudbackendmvp.repository.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.v8.crudbackendmvp.model.usuario.DetalhesExperiencias;

import java.util.List;

public interface DetalhesExperienciasRepository extends JpaRepository<DetalhesExperiencias, Long> {
    List<DetalhesExperiencias> findAllByCandidatoId(Long candidatoId);
}
