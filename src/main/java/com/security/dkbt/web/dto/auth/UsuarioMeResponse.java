package com.security.dkbt.web.dto.auth;

public record UsuarioMeResponse(
	    Long id,
	    String username,
	    String codigoEmpleado,
	    String nombreEmpleado,
	    String apellidoEmpleado,
	    String rol
	) {}