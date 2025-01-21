package com.mvp.backend.backendmvp.repository;

import com.mvp.backend.backendmvp.model.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, Long> {
    @Query("SELECT v FROM Vaga v WHERE v.estadoLogico = true")
    List<Vaga> findAllAtivos();

    @Query("SELECT v FROM Vaga v WHERE v.estadoLogico = true AND v.id = :id")
    Optional<Vaga> findAtivoById(@Param("id") long id);
}
