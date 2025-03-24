package tech.v8.crudbackendmvp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.v8.crudbackendmvp.model.vaga.VagaAplicada;

import java.util.List;
import java.util.Optional;

public interface VagaAplicadaRepository extends JpaRepository<VagaAplicada, Long> {
    Optional<VagaAplicada> findByResultadoFinalId(Long resultadoFinalId);

    @Query(value = "SELECT * FROM vagas_aplicadas v WHERE (:status IS NULL OR lower(cast(v.status as text)) LIKE lower(concat('%', :status, '%')))",
    nativeQuery = true)
    List<VagaAplicada> findAllAtivosByFilters(String status);


}
