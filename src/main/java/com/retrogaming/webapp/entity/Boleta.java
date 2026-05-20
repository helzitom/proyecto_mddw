package com.retrogaming.webapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "boletas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Boleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String numero;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(name = "cliente_nombre", nullable = false)
    private String clienteNombre;

    @Column(name = "cliente_dni", nullable = false, length = 20)
    private String clienteDni;

    @Column(name = "metodo_pago", nullable = false, length = 50)
    private String metodoPago;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @OneToMany(
            mappedBy = "boleta",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<DetalleBoleta> detalles;
}