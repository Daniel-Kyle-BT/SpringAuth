package com.security.dkbt.infrastructure.persistence.seeder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.security.dkbt.infrastructure.persistence.repository.UsuarioRepository;
import com.security.dkbt.infrastructure.persistence.usecase.RegisterService;
import com.security.dkbt.web.dto.auth.RegisterRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UsuarioSeeder implements CommandLineRunner {

	private final RegisterService registerService;
	private final UsuarioRepository usuarioRepository;

	@Override
	public void run(String... args) {
		
		if (usuarioRepository.count() > 0) {
			return;
		}

		crear("admin", "admin@test.com", "123456", "EMP00001");
		crear("user1", "user1@test.com", "123456", "EMP00002");
		crear("user2", "user2@test.com", "123456", "EMP00003");
	}

	private void crear(String username, String correo, String password, String codigoEmpleado) {

		registerService.registrar(new RegisterRequest(username, password, correo, codigoEmpleado));
	}
}