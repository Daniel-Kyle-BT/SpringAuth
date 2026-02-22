package com.security.dkbt.web.dto.auth;

public record LoginResponse (
		String access_token,
        String token_type,
        long expires_in
) {}
