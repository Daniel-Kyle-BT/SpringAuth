package com.security.dkbt.web.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRequest(
		@NotBlank(message = "El usuario es obligatorio")
	    String username,
	    
	    @NotBlank(message = "La contraseña es obligatoria")
	    String password,
	    
	    @Email
	    String correo,
	    
	    @NotNull(message = "El idEmpleado es obligatoria")
	    Long idEmpleado   
) {}


