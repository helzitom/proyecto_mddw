package com.retrogaming.webapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecuperarDTO {

    @NotBlank
    private String usuario;

    @NotBlank
    private String password;

    @NotBlank
    private String confirm;
}