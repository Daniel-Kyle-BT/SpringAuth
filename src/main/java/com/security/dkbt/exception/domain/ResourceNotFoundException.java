package com.security.dkbt.exception.domain;

public abstract class ResourceNotFoundException extends DomainException {

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message) {
        super(message);
    }
}
