package tech.v8.crudbackendmvp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.v8.crudbackendmvp.model.Funcionario;

import java.util.List;
import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    @Query("SELECT f FROM Funcionario f WHERE f.estadoLogico = true")
    List<Funcionario> findAllAtivos();

    @Query("SELECT f FROM Funcionario f WHERE f.estadoLogico = true AND f.id = :id")
    Optional<Funcionario> findAtivoById(@Param("id") long id);
}
