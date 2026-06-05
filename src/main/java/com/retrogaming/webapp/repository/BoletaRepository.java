package com.retrogaming.webapp.repository;

import com.retrogaming.webapp.model.Boleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoletaRepository
        extends JpaRepository<Boleta, Long> {
}