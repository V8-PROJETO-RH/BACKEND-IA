package com.mvp.backend.backendmvp.repository;

import com.mvp.backend.backendmvp.model.AderenciaVaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AderenciaVagaRepository extends JpaRepository<AderenciaVaga, Long> {
}
