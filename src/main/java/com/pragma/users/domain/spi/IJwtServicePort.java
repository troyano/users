package com.pragma.users.domain.spi;

import java.time.Duration;

import com.pragma.users.domain.model.UserAuth;

public interface IJwtServicePort {

	String generateToken(UserAuth user, Duration ofMinutes);
}
