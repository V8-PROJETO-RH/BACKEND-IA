package tech.v8.crudbackendmvp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.v8.crudbackendmvp.model.vaga.VagaAplicada;

public interface VagaAplicadaRepository extends JpaRepository<VagaAplicada, Long> {

}
