package tech.v8.crudbackendmvp.repository.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.v8.crudbackendmvp.model.usuario.DetalhesIdiomas;

import java.util.List;

public interface DetalhesIdiomasRepository extends JpaRepository<DetalhesIdiomas, Long> {
    List<DetalhesIdiomas> findAllByCandidatoId(Long candidatoId);
}
