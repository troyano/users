package com.pragma.users.domain.exception;

public class ValidationDatExistException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ValidationDatExistException(final String message) {
        super(message);
    }
}
