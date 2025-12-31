package com.security.dkbt.service;

import java.util.List;

import com.security.dkbt.dto.RegistrarUsuarioRequest;
import com.security.dkbt.entity.UsuarioEntity;

public interface UsuarioService {

	void registrarUsuario(RegistrarUsuarioRequest dto);
    
    List<UsuarioEntity> listarUsuarios();
}
