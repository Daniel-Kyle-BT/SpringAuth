package com.security.dkbt.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginUsuarioRequest(
		@NotBlank(message = "El usuario es obligatorio")
        String username,
        
        @NotBlank(message = "La contraseña es obligatoria")
        String password
) {}
