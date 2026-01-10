package com.security.dkbt.config.error.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.security.dkbt.config.error.ProblemBuilder;
import com.security.dkbt.config.error.ProblemWriter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(
        HttpServletRequest request,
        HttpServletResponse response,
        AccessDeniedException ex
    ) throws IOException {

        ProblemDetail pd = ProblemBuilder.springProblem(
                HttpStatus.FORBIDDEN,
                "No tienes permisos para este recurso",
                "security-forbidden",
                "SECURITY"
        );

        ProblemWriter.write(response, pd, request);
    }
}
