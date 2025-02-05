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

    @Query("SELECT v FROM Vaga v WHERE v.estadoLogico = true and lower(v.nome) like lower(concat('%', :nome, '%'))")
    List<Vaga> findAllAtivosByNome(@Param("nome") String nome);

    @Query("SELECT v FROM Vaga v WHERE v.estadoLogico = true and lower(v.modelo) like lower(concat('%', :modelo, '%'))")
    List<Vaga> findAllAtivosByModelo(@Param("modelo") String modelo);

    @Query("SELECT v FROM Vaga v WHERE v.estadoLogico = true and lower(v.localidade) like lower(concat('%', :local, '%'))")
    List<Vaga> findAllAtivosByLocal(@Param("local") String local);

}
