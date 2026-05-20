package com.retrogaming.webapp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetalleBoletaDTO {

    @NotBlank(message = "Ingrese producto")
    private String nombreProducto;

    @NotNull(message = "Ingrese precio")
    private BigDecimal precioUnitario;

    @Min(value = 1, message = "Cantidad inválida")
    private Integer cantidad;

    @NotNull
    private BigDecimal subtotal;
}