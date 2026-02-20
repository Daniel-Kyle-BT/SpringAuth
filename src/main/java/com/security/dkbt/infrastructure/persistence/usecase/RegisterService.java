package com.security.dkbt.infrastructure.persistence.usecase;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.dkbt.infrastructure.persistence.entity.RolEntity;
import com.security.dkbt.infrastructure.persistence.entity.UsuarioEntity;
import com.security.dkbt.infrastructure.persistence.repository.RolRepository;
import com.security.dkbt.infrastructure.persistence.repository.UsuarioRepository;
import com.security.dkbt.web.dto.auth.RegisterRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RegisterService {

	private final UsuarioRepository usuarioRepository;
	private final RolRepository rolRepository;
	private final PasswordEncoder passwordEncoder;

	public UsuarioEntity registrar(RegisterRequest request) {

		if (usuarioRepository.existsByUsername(request.username()))
			throw new RuntimeException("Username ya existe");

		if (usuarioRepository.existsByCorreo(request.correo()))
			throw new RuntimeException("Correo ya existe");

		RolEntity rol = rolRepository.findByNombre("ROLE_USER").orElseThrow();

		UsuarioEntity user = new UsuarioEntity();
		user.setUsername(request.username());
		user.setCorreo(request.correo());
		user.setIdEmpleado((long) 1222);
		user.setRol(rol);

		user.setPasswordHash(passwordEncoder.encode(request.password()));

		user.setEstado(true);

		return usuarioRepository.save(user);
	}

}