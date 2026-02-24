package com.security.dkbt.infrastructure.external.dto;

public record EmpleadoResponse(
	    Long id,
	    String nombre,
	    String apellido,
	    String email,
	    String documento,
	    String telefono,
	    String codigo,
	    boolean activo
	) {}