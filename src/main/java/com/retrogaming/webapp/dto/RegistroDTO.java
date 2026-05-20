package com.retrogaming.webapp.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroDTO {

    @NotBlank(message = "Ingrese usuario")
    private String usuario;

    @Email(message = "Correo inválido")
    @NotBlank(message = "Ingrese correo")
    private String correo;

    @Size(min = 4, message = "Mínimo 4 caracteres")
    private String password;

    @NotBlank(message = "Confirme contraseña")
    private String confirmPassword;

    @NotBlank(message = "Seleccione rol")
    private String rol;
}