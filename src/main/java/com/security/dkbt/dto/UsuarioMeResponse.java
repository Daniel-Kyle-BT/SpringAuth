package com.security.dkbt.dto;

import java.util.List;

public record UsuarioMeResponse(
        String username,
        List<String> roles
) {}