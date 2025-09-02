package com.pragma.users.infrastructure.out.security;

import java.time.Duration;

import com.nimbusds.jose.JOSEException;
import com.pragma.users.domain.model.UserAuth;
import com.pragma.users.domain.spi.IJwtServicePort;
import com.pragma.users.infrastructure.configuration.JwtService;

public class JwtServiceAdapter implements IJwtServicePort {
	private final JwtService jwtService;

	public JwtServiceAdapter(JwtService jwtService) {
		this.jwtService = jwtService;
	}

	@Override
	public String generateToken(UserAuth user, Duration ofMinutes) {
		return jwtService.generateToken(user, ofMinutes);
	}
}
