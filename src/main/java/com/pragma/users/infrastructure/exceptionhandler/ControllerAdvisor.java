package com.pragma.users.infrastructure.exceptionhandler;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.pragma.users.domain.exception.AuthSecurityException;
import com.pragma.users.domain.exception.ValidationException;
import com.pragma.users.infrastructure.exception.DomainNotificationException;
import com.pragma.users.infrastructure.exception.NoDataFoundException;

@ControllerAdvice
public class ControllerAdvisor {

	private static final String MESSAGE = "message";

	@ExceptionHandler(NoDataFoundException.class)
	public ResponseEntity<Map<String, String>> handleNoDataFoundException(
			NoDataFoundException ignoredNoDataFoundException) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(Collections.singletonMap(MESSAGE, ExceptionResponse.NO_DATA_FOUND.getMessage()));
	}

	@ExceptionHandler(DomainNotificationException.class)
	public ResponseEntity<String> handleDomainNotificationException(DomainNotificationException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<String> handleValidationException(ValidationException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}

	@ExceptionHandler(AuthSecurityException.class)
	public ResponseEntity<String> handleValidationException(AuthSecurityException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
	}
}
