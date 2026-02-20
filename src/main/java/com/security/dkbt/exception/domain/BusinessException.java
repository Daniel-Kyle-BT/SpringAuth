package com.security.dkbt.exception.domain;

public abstract class BusinessException extends DomainException {

	private static final long serialVersionUID = 1L;

	public BusinessException(String message) {
        super(message);
    }
}