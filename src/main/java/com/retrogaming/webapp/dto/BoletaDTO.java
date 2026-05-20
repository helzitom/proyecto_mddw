package com.retrogaming.webapp.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoletaDTO {

    @NotBlank(message = "Ingrese número")
    private String numero;

    @NotBlank(message = "Ingrese cliente")
    private String clienteNombre;

    @NotBlank(message = "Ingrese DNI")
    private String clienteDni;

    @NotBlank(message = "Seleccione método")
    private String metodoPago;

    @NotNull(message = "Ingrese total")
    private BigDecimal total;

    @Valid
    @NotEmpty(message = "Ingrese detalles")
    private List<DetalleBoletaDTO> detalles;
}