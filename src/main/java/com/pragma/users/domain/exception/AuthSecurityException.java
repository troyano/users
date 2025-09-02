package com.pragma.users.domain.exception;

public class AuthSecurityException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AuthSecurityException(final String message) {
        super(message);
    }
}
