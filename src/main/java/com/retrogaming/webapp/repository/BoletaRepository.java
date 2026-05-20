package com.retrogaming.webapp.repository;

import com.retrogaming.webapp.entity.Boleta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoletaRepository
        extends JpaRepository<Boleta, Long> {
}