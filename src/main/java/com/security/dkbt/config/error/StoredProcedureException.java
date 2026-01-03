package com.security.dkbt.config.error;

public abstract class StoredProcedureException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private final int spStatusCode;

    protected StoredProcedureException(int spStatusCode, String message) {
        super(message);
        this.spStatusCode = spStatusCode;
    }

    public int getSpStatusCode() {
        return spStatusCode;
    }
}
