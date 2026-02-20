package com.security.dkbt.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.dkbt.config.jwt.JwtUserDetails;
import com.security.dkbt.config.jwt.JwtUtil;
import com.security.dkbt.infrastructure.persistence.entity.UsuarioEntity;
import com.security.dkbt.infrastructure.persistence.usecase.RegisterService;
import com.security.dkbt.web.dto.auth.LoginRequest;
import com.security.dkbt.web.dto.auth.LoginResponse;
import com.security.dkbt.web.dto.auth.RegisterRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

	private final RegisterService registerService;
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;

	@PostMapping("/auth/register")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
		registerService.registrar(request);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/me")
	public String me(Authentication auth) {
		JwtUserDetails user = (JwtUserDetails) auth.getPrincipal();
		
		return "Empleado: " + user.getEmpleadoId();
	}

	@PostMapping("/auth/login")
	public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {

		Authentication auth = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));

		UsuarioEntity user = (UsuarioEntity) auth.getPrincipal();

		String token = jwtUtil.generarToken(user);

		return ResponseEntity.ok(new LoginResponse(token));
	}
}