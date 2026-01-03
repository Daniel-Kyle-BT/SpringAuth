package com.security.dkbt.config.error;

public class SpTechnicalException extends StoredProcedureException {

	private static final long serialVersionUID = 1L;

	public SpTechnicalException(int spStatusCode, String message) {
        super(spStatusCode, message);
    }
}
