package com.security.dkbt.web.dto.auth;

public record RegisterResponse(
        Long userId,
        String username,
        String nombre,
        String apellido,
        String status
) {}
