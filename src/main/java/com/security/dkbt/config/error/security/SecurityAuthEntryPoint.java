package com.security.dkbt.config.error.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.security.dkbt.config.error.ProblemBuilder;
import com.security.dkbt.config.error.ProblemWriter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException ex
    ) throws IOException {
    	System.out.println("🔥 ENTRY POINT EJECUTADO");

        ProblemDetail pd = ProblemBuilder.springProblem(
                HttpStatus.UNAUTHORIZED,
                "Credenciales inválidas",
                "security-auth",
                "SECURITY"
        );

        ProblemWriter.write(response, pd, request);
    }
}