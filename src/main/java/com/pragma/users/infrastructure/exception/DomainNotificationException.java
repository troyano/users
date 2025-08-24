package com.pragma.users.infrastructure.exception;

public class DomainNotificationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DomainNotificationException(String message) {
		super(message);
	}
}
