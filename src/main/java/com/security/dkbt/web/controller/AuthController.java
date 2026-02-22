package com.security.dkbt.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.dkbt.config.jwt.JwtUserDetails;
import com.security.dkbt.config.jwt.JwtUtil;
import com.security.dkbt.infrastructure.persistence.usecase.RegisterService;
import com.security.dkbt.web.dto.auth.LoginRequest;
import com.security.dkbt.web.dto.auth.LoginResponse;
import com.security.dkbt.web.dto.auth.MeResponse;
import com.security.dkbt.web.dto.auth.RegisterRequest;
import com.security.dkbt.web.dto.auth.RegisterResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final RegisterService registerService;
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;

	@PostMapping("/register")
	public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {	
		return ResponseEntity.status(HttpStatus.CREATED).body(registerService.registrar(request));
	}

	@GetMapping("/me")
	public MeResponse me(@AuthenticationPrincipal JwtUserDetails user) {

		List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

		return new MeResponse(user.getUsername(), user.getEmpleadoId(), roles, true);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {

		Authentication auth = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));

		JwtUserDetails user = (JwtUserDetails) auth.getPrincipal();

		String token = jwtUtil.generarToken(user);
		
		LoginResponse response = new LoginResponse(
	            token,
	            "Bearer",
	            jwtUtil.getExpirationSeconds()
	    );

		return ResponseEntity.ok(response);
	}
}