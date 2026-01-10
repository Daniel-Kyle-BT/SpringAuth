package com.security.dkbt.config.error;

import java.io.IOException;
import java.net.URI;

import org.springframework.http.ProblemDetail;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public final class ProblemWriter {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private ProblemWriter() {}

    public static void write(
            HttpServletResponse response,
            ProblemDetail pd,
            HttpServletRequest request
    ) throws IOException {
        if (pd.getInstance() == null && request != null) {
            pd.setInstance(URI.create(request.getRequestURI()));
        }
        response.setStatus(pd.getStatus());
        response.setContentType("application/problem+json");
        MAPPER.writeValue(response.getWriter(), pd);
    }
}