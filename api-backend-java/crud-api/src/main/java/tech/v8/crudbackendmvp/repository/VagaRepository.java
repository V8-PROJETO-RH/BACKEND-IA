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

    @Query(value = "SELECT * FROM vaga v WHERE v.estado_logico = true " +
            "AND (:nome IS NULL OR v.nome ILIKE CONCAT('%', :nome, '%')) " +
            "AND (:modelo IS NULL OR v.modelo = CAST(:modelo AS VARCHAR)) " +
            "AND (:local IS NULL OR v.localidade ILIKE CONCAT('%', :local, '%')) " +
            "AND (:status IS NULL OR v.status = CAST(:status AS VARCHAR))",
            nativeQuery = true)
    List<Vaga> findAllAtivosByFilters(
            @Param("nome") String nome,
            @Param("modelo") String modelo,
            @Param("local") String local,
            @Param("status") String status);

}
