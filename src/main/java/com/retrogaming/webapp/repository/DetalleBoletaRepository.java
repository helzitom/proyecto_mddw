package com.retrogaming.webapp.repository;

import com.retrogaming.webapp.model.DetalleBoleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleBoletaRepository
        extends JpaRepository<DetalleBoleta, Long> {
}