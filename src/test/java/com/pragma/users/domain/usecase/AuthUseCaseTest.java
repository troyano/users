package com.pragma.users.domain.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.Duration;

import com.pragma.users.domain.exception.AuthSecurityException;
import com.pragma.users.domain.model.Login;
import com.pragma.users.domain.model.TokenResponse;
import com.pragma.users.domain.model.UserAuth;
import com.pragma.users.domain.spi.IJwtServicePort;
import com.pragma.users.domain.spi.IPasswordEncoderPort;
import com.pragma.users.domain.spi.IUserPersistencePort;
import com.pragma.users.domain.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AuthUseCaseTest {
    @Mock private IUserPersistencePort userPersistencePort;
    @Mock private IPasswordEncoderPort passwordEncoderPort;
    @Mock private IJwtServicePort jwtServicePort;

    @InjectMocks private AuthUseCase authUseCase;

    private Login loginRequest;
    private UserAuth userAuth;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        loginRequest = new Login();
        loginRequest.setEmail("user@test.com");
        loginRequest.setPassword("password");
        userAuth = new UserAuth();
        userAuth.setEmail("user@test.com");
        userAuth.setPassword("encodedPassword");
        authUseCase = new AuthUseCase(userPersistencePort, jwtServicePort, passwordEncoderPort);
    }

    @Test
    void auth_ok() {
        when(userPersistencePort.userByEmail(loginRequest.getEmail())).thenReturn(userAuth);
        when(passwordEncoderPort.matches(loginRequest.getPassword(), userAuth.getPassword())).thenReturn(true);
        when(jwtServicePort.generateToken(userAuth, Duration.ofMinutes(Constants.NUMBER_60))).thenReturn("token");

        TokenResponse response = authUseCase.auth(loginRequest);
        assertEquals("token", response.getAccessToken());
        assertEquals(Constants.TOKEN_TIME, response.getExpiresInSeconds());
    }

    @Test
    void auth_userNotFound() {
        when(userPersistencePort.userByEmail(loginRequest.getEmail())).thenReturn(null);
        AuthSecurityException exception = assertThrows(AuthSecurityException.class, () -> authUseCase.auth(loginRequest));
        assertEquals(Constants.MSG_INVALID_CREDENTIALS, exception.getMessage());
    }

    @Test
    void auth_invalidPassword() {
        when(userPersistencePort.userByEmail(loginRequest.getEmail())).thenReturn(userAuth);
        when(passwordEncoderPort.matches(loginRequest.getPassword(), userAuth.getPassword())).thenReturn(false);
        AuthSecurityException exception = assertThrows(AuthSecurityException.class, () -> authUseCase.auth(loginRequest));
        assertEquals(Constants.MSG_INVALID_CREDENTIALS, exception.getMessage());
    }
}