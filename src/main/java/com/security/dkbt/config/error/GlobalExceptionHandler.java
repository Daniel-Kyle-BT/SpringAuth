package com.security.dkbt.config.error;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	/*
	ERRORES DESDE STORED PROCEDURE
	*/

	@ExceptionHandler(StoredProcedureException.class)
	ProblemDetail handleStoredProcedure(StoredProcedureException ex) {

		SpStatusCode spCode = SpStatusCode.from(ex.getSpStatusCode());

		ProblemDetail pd = ProblemDetail.forStatusAndDetail(spCode.getHttpStatus(), ex.getMessage());

		pd.setTitle("Error desde Stored Procedure");
		pd.setType(URI.create("https://api.dkbt.com/errors/db-sp"));
		pd.setProperty("origin", "DATABASE");
		pd.setProperty("spStatusCode", ex.getSpStatusCode());

		return pd;
	}
	
    /*
    ERRORES SPRING / SECURITY
    */	
	
    @ExceptionHandler(IllegalStateException.class)
    ProblemDetail handleIllegalState(IllegalStateException ex) {
        return springProblem(HttpStatus.BAD_REQUEST, ex.getMessage(), "business-rule");
    }
    
    @ExceptionHandler(UsernameNotFoundException.class)
    ProblemDetail handleUserNotFound(UsernameNotFoundException ex) {
        return springProblem(HttpStatus.UNAUTHORIZED, ex.getMessage(), "auth");
    }

    @ExceptionHandler(BadCredentialsException.class)
    ProblemDetail handleBadCredentials() {
        return springProblem(HttpStatus.UNAUTHORIZED, "Credenciales inválidas", "auth");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .orElse("Datos inválidos");

        ProblemDetail pd = springProblem(HttpStatus.BAD_REQUEST, msg, "validation");
        pd.setProperty("errors", ex.getBindingResult().getFieldErrors());
        return pd;
    }

    @ExceptionHandler(Exception.class)
    ProblemDetail handleGeneric(Exception ex) {
        return springProblem(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor", "internal");
    }

    private ProblemDetail springProblem(HttpStatus status, String detail, String type) {
    	
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(status, detail);
        pd.setTitle(status.getReasonPhrase());
        pd.setType(URI.create("https://api.dkbt.com/errors/spring-" + type));
        pd.setProperty("origin", "SPRING");
        
        return pd;
    }
}