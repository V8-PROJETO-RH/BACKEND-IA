package tech.v8.crudbackendmvp.repository.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.v8.crudbackendmvp.model.usuario.Candidato;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidatoRepository extends JpaRepository<Candidato, Long> {

    @Query("SELECT c FROM Candidato c WHERE c.estadoLogico = true")
    List<Candidato> findAllAtivos();

    @Query("SELECT c FROM Candidato c WHERE c.estadoLogico = true AND c.id = :id")
    Optional<Candidato> findAtivoById(@Param("id") long id);
}
