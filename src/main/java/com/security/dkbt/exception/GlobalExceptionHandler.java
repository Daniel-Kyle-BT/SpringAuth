package com.security.dkbt.exception;

import java.util.Map;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.security.dkbt.exception.domain.BusinessException;
import com.security.dkbt.exception.domain.ResourceNotFoundException;
import com.security.dkbt.exception.problem.ProblemFactory;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

	private final ProblemFactory probFac;

	// VALIDACIONES @Valid
	@ExceptionHandler(MethodArgumentNotValidException.class)
	ProblemDetail handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
		ProblemDetail pd = probFac.build(HttpStatus.BAD_REQUEST, "Datos inválidos", "validation-error", request, ex);

		pd.setProperty("errors", ex.getBindingResult().getFieldErrors().stream()
				.map(err -> Map.of("field", err.getField(), "message", err.getDefaultMessage())).toList());

		return pd;
	}

	// REGLAS DE NEGOCIO
	@ExceptionHandler(IllegalStateException.class)
	ProblemDetail handleBusiness(IllegalStateException ex, HttpServletRequest request) {
		return probFac.build(HttpStatus.BAD_REQUEST, ex.getMessage(), "business-rule", request, ex);
	}

	// BUSINESS RULE (400)
	@ExceptionHandler(BusinessException.class)
	ProblemDetail handleBusiness(BusinessException ex, HttpServletRequest request) {
		return probFac.build(HttpStatus.BAD_REQUEST, ex.getMessage(), "business-rule", request, ex);
	}

	// NOT FOUND
	@ExceptionHandler(ResourceNotFoundException.class)
	ProblemDetail handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
		return probFac.build(HttpStatus.NOT_FOUND, ex.getMessage(), "not-found", request, ex);
	}

	@ExceptionHandler(NotFoundException.class)
	ProblemDetail handleNotFound(NotFoundException ex, HttpServletRequest request) {
		return probFac.build(HttpStatus.NOT_FOUND, ex.getMessage(), "not-found", request, ex);
	}

	// AUTH / SECURITY
	@ExceptionHandler(AuthenticationException.class)
	ProblemDetail handleAuth(AuthenticationException ex, HttpServletRequest request) {
		return probFac.build(HttpStatus.UNAUTHORIZED, "No autenticado", "unauthorized", request, ex);
	}

	@ExceptionHandler(AccessDeniedException.class)
	ProblemDetail handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
		return probFac.build(HttpStatus.FORBIDDEN, "Acceso denegado", "forbidden", request, ex);
	}

	@ExceptionHandler(Exception.class)
	ProblemDetail handleGeneric(Exception ex, HttpServletRequest request) {
		return probFac.build(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor", "internal-error", request,
				ex);
	}
}
