package com.security.dkbt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.dkbt.dto.RegistrarUsuarioRequest;
import com.security.dkbt.dto.UsuarioMeResponse;
import com.security.dkbt.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    
    private final UsuarioService usuarioService;

    @PostMapping("/auth/register")
    public ResponseEntity<?> registrar(@Valid @RequestBody RegistrarUsuarioRequest request) {
        usuarioService.registrarUsuario(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @GetMapping("/me")
    public UsuarioMeResponse me(Authentication authentication) {	
    	return usuarioService.obtenerMe(authentication.getName());
    }
}