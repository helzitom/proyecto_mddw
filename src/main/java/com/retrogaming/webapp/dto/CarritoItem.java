package com.retrogaming.webapp.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarritoItem {

    private String nombre;

    private BigDecimal precio;

    private Integer cantidad;

    private String imagen;
}