package com.security.dkbt.service.impl;

import com.security.dkbt.dto.RegistrarUsuarioRequest;
import com.security.dkbt.entity.UsuarioEntity;
import com.security.dkbt.repository.UsuarioProcedureRepository;
import com.security.dkbt.repository.UsuarioRepository;
import com.security.dkbt.service.UsuarioService;

import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

	private final UsuarioRepository usuarioRepository;
    private final UsuarioProcedureRepository usuarioProcedureRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void registrarUsuario(RegistrarUsuarioRequest dto) {
		Long idEmpleado = null;
		Integer idRol;
		/* ===== SP1: VALIDACIÓN EMPLEADO (opcional) ===== */
		if (dto.codigoEmpleado() != null) {
			Map<String, Object> sp1 = usuarioProcedureRepository.validarEmpleadoParaUsuario(dto.codigoEmpleado());
			Integer status = (Integer) sp1.get("_StatusCode");
			if (status != 200) {
				throw new IllegalStateException((String) sp1.get("_Message"));
			}
			idEmpleado = ((Number) sp1.get("_ID_EMPLEADO")).longValue();
			idRol = ((Number) sp1.get("_ID_ROL")).intValue();
			
		} else {
			idRol = 1; 
		}
		String passwordHash = passwordEncoder.encode(dto.password());
		/* ===== SP2: creación ===== */
		Map<String, Object> sp2 = usuarioProcedureRepository.crearUsuario(idEmpleado, idRol, dto.username(), passwordHash);
		Integer status = (Integer) sp2.get("_StatusCode");
		if (status != 200) {
			throw new IllegalStateException((String) sp2.get("_Message"));
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UsuarioEntity usuario = usuarioRepository.findAuthUser(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado o inactivo"));
		
		var authority = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getNombre().toUpperCase());
		
		return new User(usuario.getUsername(), usuario.getPasswordHash(), usuario.getEstado(),true, true, true,
				Collections.singletonList(authority));
	}

	@Override
	public List<UsuarioEntity> listarUsuarios() {
		return usuarioRepository.findAll();
	}
}