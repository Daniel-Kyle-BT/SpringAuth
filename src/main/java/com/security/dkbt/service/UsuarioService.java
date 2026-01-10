package com.security.dkbt.service;

import com.security.dkbt.dto.RegistrarUsuarioRequest;
import com.security.dkbt.dto.UsuarioMeResponse;

public interface UsuarioService {

	void registrarUsuario(RegistrarUsuarioRequest dto);
    
	UsuarioMeResponse obtenerMe(String username);
}
