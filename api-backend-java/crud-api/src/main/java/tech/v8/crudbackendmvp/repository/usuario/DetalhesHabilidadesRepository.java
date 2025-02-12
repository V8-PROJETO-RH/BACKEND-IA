package tech.v8.crudbackendmvp.repository.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.v8.crudbackendmvp.model.usuario.DetalhesHabilidades;

import java.util.List;

public interface DetalhesHabilidadesRepository extends JpaRepository<DetalhesHabilidades, Long> {
    List<DetalhesHabilidades> findAllByCandidatoId(Long candidatoId);
}
