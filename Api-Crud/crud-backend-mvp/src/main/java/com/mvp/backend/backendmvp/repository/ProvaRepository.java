package com.mvp.backend.backendmvp.repository;

import com.mvp.backend.backendmvp.model.Prova;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvaRepository extends JpaRepository<Prova, Long> {
}
