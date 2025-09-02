package com.pragma.users.domain.usecase;

import java.time.Duration;

import com.pragma.users.domain.api.IAuthServicePort;
import com.pragma.users.domain.exception.AuthSecurityException;
import com.pragma.users.domain.model.Login;
import com.pragma.users.domain.model.TokenResponse;
import com.pragma.users.domain.model.UserAuth;
import com.pragma.users.domain.spi.IJwtServicePort;
import com.pragma.users.domain.spi.IPasswordEncoderPort;
import com.pragma.users.domain.spi.IUserPersistencePort;
import com.pragma.users.domain.util.Constants;

public class AuthUseCase implements IAuthServicePort {

	private final IUserPersistencePort userPersistencePort;
	private final IPasswordEncoderPort passwordEncoderPort;
	private final IJwtServicePort jwtServicePort;

	public AuthUseCase(final IUserPersistencePort userPersistencePort, final IJwtServicePort jwtServicePort,
			final IPasswordEncoderPort passwordEncoderPort) {
		this.userPersistencePort = userPersistencePort;
		this.passwordEncoderPort = passwordEncoderPort;
		this.jwtServicePort = jwtServicePort;
	}

	@Override
	public TokenResponse auth(Login loginRequest) {
		UserAuth user = userPersistencePort.userByEmail(loginRequest.getEmail());

		if (user == null) {
			throw new AuthSecurityException(Constants.MSG_INVALID_CREDENTIALS);
		}
		if (!passwordEncoderPort.matches(loginRequest.getPassword(), user.getPassword())) {
			throw new AuthSecurityException(Constants.MSG_INVALID_CREDENTIALS);
		}
		final String token = jwtServicePort.generateToken(user, Duration.ofMinutes(Constants.NUMBER_60));
		return new TokenResponse(token, Constants.TOKEN_TIME);
	}
}
