package com.security.dkbt.controller;


import java.util.HashMap;
import java.util.Optional;

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

import com.security.dkbt.config.jwt.JwtUtil;
import com.security.dkbt.dto.LoginTokenResponse;
import com.security.dkbt.dto.LoginUsuarioRequest;
import com.security.dkbt.dto.RegistrarUsuarioRequest;
import com.security.dkbt.dto.UsuarioMeResponse;
import com.security.dkbt.entity.UsuarioEntity;
import com.security.dkbt.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
	
    private final AuthenticationManager authenticationManager;
	
    private final JwtUtil jwtUtil;
    
    private final UsuarioService usuarioService;

	@PostMapping("/auth/login")
	public ResponseEntity<LoginTokenResponse> login(@RequestBody LoginUsuarioRequest request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
		String token = jwtUtil.generarToken(request.username(), new HashMap<>());

		return ResponseEntity.ok(new LoginTokenResponse(token));
	}

    @PostMapping("/auth/register")
    public ResponseEntity<?> registrar(@RequestBody RegistrarUsuarioRequest request) {
        usuarioService.registrarUsuario(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @GetMapping("/me")
    public UsuarioMeResponse me(Authentication authentication) {

        Optional<UsuarioEntity> usuario = usuarioService.obtnerUsuarioPorUsername(authentication.getName());

        return new UsuarioMeResponse(
            usuario.get().getId(),
            usuario.get().getUsername(),
            usuario.get().getEmpleado().getCodigo(),
            usuario.get().getEmpleado().getNombre(),
            usuario.get().getEmpleado().getApellido(),
            usuario.get().getRol().getNombre()
        );
    }
}