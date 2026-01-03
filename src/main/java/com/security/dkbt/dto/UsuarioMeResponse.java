package com.security.dkbt.dto;

public record UsuarioMeResponse(
	    Long id,
	    String username,
	    String codigoEmpleado,
	    String nombreEmpleado,
	    String apellidoEmpleado,
	    String rol
	) {}