package tech.v8.crudbackendmvp.repository.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.v8.crudbackendmvp.model.usuario.DetalhesFormacao;

import java.util.List;

public interface DetalhesFormacaoRepository extends JpaRepository<DetalhesFormacao, Long> {
    List<DetalhesFormacao> findAllByCandidatoId(Long candidatoId);
}
