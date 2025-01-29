package tech.v8.crudbackendmvp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.v8.crudbackendmvp.model.AderenciaVaga;

@Repository
public interface AderenciaVagaRepository extends JpaRepository<AderenciaVaga, Long> {
}
