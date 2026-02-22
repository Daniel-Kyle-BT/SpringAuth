package com.security.dkbt.web.dto.auth;

import java.util.List;

public record MeResponse(
        String username,
        Long empleadoId,
        List<String> roles,
        boolean authenticated
) {}