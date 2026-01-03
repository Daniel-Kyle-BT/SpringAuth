package com.security.dkbt.config.error;

public class SpBusinessException extends StoredProcedureException {

	private static final long serialVersionUID = 1L;

	public SpBusinessException(int spStatusCode, String message) {
        super(spStatusCode, message);
    }
}
