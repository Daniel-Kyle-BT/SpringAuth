package com.security.dkbt.infrastructure.persistence.usecase;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.dkbt.infrastructure.external.EmpleadoClient;
import com.security.dkbt.infrastructure.persistence.entity.RolEntity;
import com.security.dkbt.infrastructure.persistence.entity.UsuarioEntity;
import com.security.dkbt.infrastructure.persistence.repository.RolRepository;
import com.security.dkbt.infrastructure.persistence.repository.UsuarioRepository;
import com.security.dkbt.web.dto.auth.RegisterRequest;
import com.security.dkbt.web.dto.auth.RegisterResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RegisterService {

	private final EmpleadoClient empleadoClient;
	private final UsuarioRepository usuarioRepository;
	private final RolRepository rolRepository;
	private final PasswordEncoder passwordEncoder;

	public RegisterResponse registrar(RegisterRequest request) {

		String username = request.username().trim().toLowerCase();
		String correo = request.correo().trim().toLowerCase();

		if (usuarioRepository.existsByUsername(username))
			throw new IllegalStateException("Username ya existe");

		if (usuarioRepository.existsByCorreo(correo))
			throw new IllegalStateException("Correo ya existe");
		
		var empleado = empleadoClient.obtenerPorCodigo(request.codigoEmpleado());

		RolEntity rol = rolRepository.findByNombre("ROLE_USER").orElseThrow();

		UsuarioEntity user = new UsuarioEntity();
		user.setUsername(username);
		user.setCorreo(correo);
		user.setIdEmpleado(empleado.id());
		user.setRol(rol);
		user.setPasswordHash(passwordEncoder.encode(request.password()));
		user.setEstado(true);

		UsuarioEntity userGuard = usuarioRepository.save(user);

		return new RegisterResponse(userGuard.getId(), userGuard.getUsername(), empleado.nombre(), empleado.apellido(), "CREATED");
	}

}