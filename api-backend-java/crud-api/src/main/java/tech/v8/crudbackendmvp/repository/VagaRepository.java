package tech.v8.crudbackendmvp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.v8.crudbackendmvp.model.vaga.Vaga;
import tech.v8.crudbackendmvp.model.vaga.VagaAplicada;

import java.util.List;
import java.util.Optional;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, Long> {
    @Query("SELECT v FROM Vaga v WHERE v.estadoLogico = true")
    List<Vaga> findAllAtivos();

    @Query("SELECT v FROM Vaga v WHERE v.estadoLogico = true AND v.id = :id")
    Optional<Vaga> findAtivoById(@Param("id") long id);

    @Query("SELECT va FROM VagaAplicada va WHERE va.vaga.id = :id")
    List<VagaAplicada> findAllVagasAplicadasByVagaId(Long id);

    @Query(value = "SELECT * FROM Vaga v WHERE v.estado_logico = true " +
            "AND (:nome IS NULL OR lower(cast(v.nome as text)) LIKE lower(concat('%', :nome, '%'))) " +
            "AND (:modelo IS NULL OR lower(cast(v.modelo as text)) LIKE lower(concat('%', :modelo, '%'))) " +
            "AND (:local IS NULL OR lower(cast(v.localidade as text)) LIKE lower(concat('%', :local, '%')))"
            , nativeQuery = true)
    List<Vaga> findAllAtivosByFilters(String nome, String modelo, String local);

}
