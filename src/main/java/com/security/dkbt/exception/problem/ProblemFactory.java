package com.security.dkbt.exception.problem;

import java.net.URI;
import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProblemFactory {

	private final ProblemProperties properties;

	public ProblemDetail build(HttpStatus status, String detail, String type, HttpServletRequest request,
			Exception ex) {

		ProblemDetail pd = ProblemDetail.forStatusAndDetail(status, detail);

		pd.setTitle(status.getReasonPhrase());
		pd.setType(URI.create("https://api.dkbt.com/errors/" + type));

		if (request != null) {
			pd.setInstance(URI.create(request.getRequestURI()));
		}

		pd.setProperty("timestamp", Instant.now());

		if (properties.isDebug() && ex != null) {
			pd.setProperty("exception", ex.getClass().getSimpleName());
			pd.setProperty("message", ex.getMessage());
			pd.setProperty("path", request.getRequestURI());
		}

		return pd;
	}
}
