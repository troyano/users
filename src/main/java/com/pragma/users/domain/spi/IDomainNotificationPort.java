package com.pragma.users.domain.spi;

public interface IDomainNotificationPort {
	void notifyError(String message);
}
