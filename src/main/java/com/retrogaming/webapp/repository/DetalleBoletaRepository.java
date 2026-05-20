package com.retrogaming.webapp.repository;

import com.retrogaming.webapp.entity.DetalleBoleta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleBoletaRepository
        extends JpaRepository<DetalleBoleta, Long> {
}