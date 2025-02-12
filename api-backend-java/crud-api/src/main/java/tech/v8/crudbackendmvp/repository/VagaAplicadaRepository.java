package tech.v8.crudbackendmvp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.v8.crudbackendmvp.model.vaga.VagaAplicada;

import java.util.Optional;

public interface VagaAplicadaRepository extends JpaRepository<VagaAplicada, Long> {
    Optional<VagaAplicada> findByResultadoFinalId(Long resultadoFinalId);



}
