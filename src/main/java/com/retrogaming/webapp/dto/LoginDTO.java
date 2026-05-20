package com.retrogaming.webapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    @NotBlank(message = "Ingrese usuario")
    private String user;

    @NotBlank(message = "Ingrese contraseña")
    private String pass;
}