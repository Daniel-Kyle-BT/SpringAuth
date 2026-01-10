package com.security.dkbt.config.error;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.security.dkbt.config.error.sp.SpStatusCode;
import com.security.dkbt.config.error.sp.StoredProcedureException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	/*
	ERRORES DESDE STORED PROCEDURE
	*/
	@ExceptionHandler(StoredProcedureException.class)
	ProblemDetail handleStoredProcedure(StoredProcedureException ex) {
		SpStatusCode spCode = SpStatusCode.from(ex.getSpStatusCode());
		return ProblemBuilder.springProblem(spCode.getHttpStatus(), ex.getMessage(), "db-sp-business-rule", "DATABASE");
	}
	
    /*
    ERRORES SPRING / SECURITY
    */	
	@ExceptionHandler(IllegalStateException.class)
	ProblemDetail handleIllegalState(IllegalStateException ex) {
		return ProblemBuilder.springProblem(HttpStatus.BAD_REQUEST, ex.getMessage(), "spring-business-rule", "SPRING");
	}
    
    @ExceptionHandler(ResourceNotFoundException.class)
    ProblemDetail handleNotFound(ResourceNotFoundException ex) {
        return ProblemBuilder.springProblem(HttpStatus.NOT_FOUND, ex.getMessage(), "spring-not-found", "SPRING");
    }

	@ExceptionHandler(MethodArgumentNotValidException.class)
	ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
		ProblemDetail pd = ProblemBuilder.springProblem(HttpStatus.BAD_REQUEST, "Datos inválidos", "spring-validation",
				"SPRING");

		pd.setProperty("errors", ex.getBindingResult().getFieldErrors().stream()
				.map(err -> Map.of("field", err.getField(), "message", err.getDefaultMessage())).toList());

		return pd;
	}

	@ExceptionHandler(Exception.class)
	ProblemDetail handleGeneric(Exception ex) {
		return ProblemBuilder.springProblem(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor", "spring-internal",
				"SPRING");
	}

}