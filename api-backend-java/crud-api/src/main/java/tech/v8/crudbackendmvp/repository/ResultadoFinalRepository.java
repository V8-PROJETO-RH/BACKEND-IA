package tech.v8.crudbackendmvp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.v8.crudbackendmvp.model.vaga.ResultadoFinal;

import java.util.List;

@Repository
public interface ResultadoFinalRepository extends JpaRepository<ResultadoFinal, Long> {
    List<ResultadoFinal> findAllById(Long vagaId);
}
