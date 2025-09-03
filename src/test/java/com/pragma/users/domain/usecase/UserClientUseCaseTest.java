package com.pragma.users.domain.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.pragma.users.domain.exception.ValidationDatExistException;
import com.pragma.users.domain.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.pragma.users.domain.model.Role;
import com.pragma.users.domain.model.UserClient;
import com.pragma.users.domain.spi.IPasswordEncoderPort;
import com.pragma.users.domain.spi.IRolePersistencePort;
import com.pragma.users.domain.spi.IUserPersistencePort;
import com.pragma.users.domain.util.Constants;
import com.pragma.users.infrastructure.exception.NoDataFoundException;

class UserClientUseCaseTest {
    @Mock private IUserPersistencePort userPersistencePort;
    @Mock private IRolePersistencePort rolePersistencePort;
    @Mock private IPasswordEncoderPort passwordEncoderPort;

    @InjectMocks private UserClientUseCase userClientUseCase;

    private UserClient userClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userClient = new UserClient();
        userClient.setEmail("client@test.com");
        userClient.setPhone("+573005698325");
        userClient.setIdentityDocument("123456789");
        userClient.setPassword("secret");
        userClientUseCase = new UserClientUseCase(userPersistencePort, rolePersistencePort, passwordEncoderPort);
    }

    @Test
    void createClient_ok() {
        when(userPersistencePort.userExist(userClient.getEmail())).thenReturn(false);
        Role role = new Role(3L, Constants.ROLE_CLIENT, "Client");
        when(rolePersistencePort.findByCode(Constants.ROLE_CLIENT)).thenReturn(Optional.of(role));
        when(passwordEncoderPort.encode("secret")).thenReturn("encoded");

        userClientUseCase.createClient(userClient);

        assertEquals("encoded", userClient.getPassword());
        assertEquals(role.getId(), userClient.getRoleId());
        verify(userPersistencePort).saveClient(userClient);
    }

    @Test
    void createClient_invalidEmail() {
        userClient.setEmail("invalid");
        when(userPersistencePort.userExist(userClient.getEmail())).thenReturn(false);
        ValidationException exception = assertThrows(ValidationException.class, () -> userClientUseCase.createClient(userClient));
        assertEquals(Constants.MSG_INVALID_EMAIL_FORMAT, exception.getMessage());
    }

    void createClient_userAlreadyExists() {
        when(userPersistencePort.userExist(userClient.getEmail())).thenReturn(true);
        ValidationDatExistException exception = assertThrows(ValidationDatExistException.class,
                () -> userClientUseCase.createClient(userClient));
        assertEquals(Constants.MSG_USER_CLIENT_ALREADY_EXISTS, exception.getMessage());
    }

    @Test
    void createClient_invalidPhone() {
        userClient.setPhone("invalid");
        when(userPersistencePort.userExist(userClient.getEmail())).thenReturn(false);
        ValidationException exception = assertThrows(ValidationException.class, () -> userClientUseCase.createClient(userClient));
        assertEquals(Constants.MSG_CELL_PHONE_REGEX, exception.getMessage());
    }

    @Test
    void createClient_invalidId() {
        userClient.setIdentityDocument("invalid_id");
        when(userPersistencePort.userExist(userClient.getEmail())).thenReturn(false);
        ValidationException exception = assertThrows(ValidationException.class, () -> userClientUseCase.createClient(userClient));
        assertEquals(Constants.MSG_ID_NUMERIC, exception.getMessage());
    }

    @Test
    void createClient_noRole() {
        when(userPersistencePort.userExist(userClient.getEmail())).thenReturn(false);
        when(rolePersistencePort.findByCode(Constants.ROLE_CLIENT)).thenReturn(Optional.empty());
        NoDataFoundException exception = assertThrows(NoDataFoundException.class, () -> userClientUseCase.createClient(userClient));
        assertEquals(Constants.MSG_ROLE_EMPLOYEE_DOES_NOT_EXIST, exception.getMessage());
    }
}
