package com.security.dkbt.exception.problem;

import java.io.IOException;

import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProblemWriter {

    private final ObjectMapper mapper;

    public void write(
            HttpServletResponse response,
            ProblemDetail pd
    ) throws IOException {

        response.setStatus(pd.getStatus());
        response.setContentType("application/problem+json");

        mapper.writeValue(response.getOutputStream(), pd);
    }
}