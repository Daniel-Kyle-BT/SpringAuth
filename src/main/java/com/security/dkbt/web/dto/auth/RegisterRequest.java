package com.security.dkbt.web.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegisterRequest(
		@NotBlank(message = "El usuario es obligatorio")
	    String username,
	    
	    @NotBlank(message = "La contraseña es obligatoria")
	    String password,
	    
	    @Email
	    String correo,
	    
        @Pattern(
        		regexp = "^[A-Z]{3}\\d{3}$", 
        	    message = "El código de empleado debe tener 3 letras seguidas de 3 dígitos (ej. EMP001)"
            )
	    String codigoEmpleado   
) {}


