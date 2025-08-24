package com.pragma.users.infrastructure.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.pragma.users.infrastructure.exception.DomainNotificationException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DomainNotificationException.class)
	public ResponseEntity<String> handleDomainNotificationException(DomainNotificationException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}
}
