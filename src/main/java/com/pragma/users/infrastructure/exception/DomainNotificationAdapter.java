package com.pragma.users.infrastructure.exception;

import com.pragma.users.domain.spi.IDomainNotificationPort;

public class DomainNotificationAdapter implements IDomainNotificationPort {
	@Override
	public void notifyError(String message) {
		throw new DomainNotificationException(message);
	}
}
