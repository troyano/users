package com.pragma.users.domain.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.pragma.users.domain.exception.DomainException;
import com.pragma.users.domain.model.Role;
import com.pragma.users.domain.model.User;
import com.pragma.users.domain.spi.IDomainNotificationPort;
import com.pragma.users.domain.spi.IPasswordEncoderPort;
import com.pragma.users.domain.spi.IRolePersistencePort;
import com.pragma.users.domain.spi.IUserPersistencePort;
import com.pragma.users.domain.util.Constants;

class UserUseCaseTest {

    @Mock private IUserPersistencePort userPersistencePort;
    @Mock private IRolePersistencePort rolePersistencePort;
    @Mock private IPasswordEncoderPort passwordEncoderPort;
    @Mock private IDomainNotificationPort domainNotificationPort;

    @InjectMocks private UserUseCase userUseCase;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setEmail("juan@test.com");
        user.setPhone("+573005698325");
        user.setIdentityDocument("123456789");
        user.setBirthDate(LocalDate.of(Constants.NUMERO_1990, 1, 1));
        user.setPassword("secreta");
        userUseCase = new UserUseCase(userPersistencePort, rolePersistencePort, passwordEncoderPort, domainNotificationPort);

        doAnswer(invocation -> { throw new DomainException(invocation.getArgument(0)); })
            .when(domainNotificationPort).notifyError(anyString());
    }

    @Test
    void createUser_ok() {
        userUseCase.createUser(user);
        verify(userPersistencePort).saveUser(user);
    }

    @Test
    void createUser_invalidEmail() {
        user.setEmail("invalid");
        assertThrows(DomainException.class, () -> userUseCase.createUser(user));
    }

    @Test
    void createUser_invalidCellPhone() {
        user.setPhone("invalid");
        assertThrows(DomainException.class, () -> userUseCase.createUser(user));
    }

    @Test
    void createUser_invalidId() {
        user.setIdentityDocument("invalid_id");
        assertThrows(DomainException.class, () -> userUseCase.createUser(user));
    }

    @Test
    void createOwner_underage() {
        user.setBirthDate(LocalDate.now().minusYears(Constants.NUMERO_15));
        assertThrows(DomainException.class, () -> userUseCase.createOwner(user));
    }

    @Test
    void createOwner_noRole() {
        when(rolePersistencePort.findByCode(Constants.ROLE_OWNER)).thenReturn(Optional.empty());
        assertThrows(DomainException.class, () -> userUseCase.createOwner(user));
    }

    @Test
    void createOwner_ok() {
        Role role = new Role(1L, Constants.ROLE_OWNER, "Owner");
        when(rolePersistencePort.findByCode(Constants.ROLE_OWNER)).thenReturn(Optional.of(role));
        when(passwordEncoderPort.encode("secreta")).thenReturn("encoded");

        userUseCase.createOwner(user);

        assertEquals("encoded", user.getPassword());
        assertEquals(role, user.getRole());
        verify(userPersistencePort).saveUser(user);
    }

    @Test
    void createUser_invalidEmail_notifyErrorCalled() {
        user.setEmail("invalid");
        doAnswer(invocation -> null).when(domainNotificationPort).notifyError(anyString());
        userUseCase.createUser(user);
        verify(domainNotificationPort).notifyError(Constants.MSG_INVALID_EMAIL);
    }

    @Test
    void createUser_invalidCellPhone_notifyErrorCalled() {
        user.setPhone("invalid");
        doAnswer(invocation -> null).when(domainNotificationPort).notifyError(anyString());
        userUseCase.createUser(user);
        verify(domainNotificationPort).notifyError(Constants.MSG_INVALID_CELL_PHONE);
    }

    @Test
    void createUser_invalidId_notifyErrorCalled() {
        user.setIdentityDocument("invalid_id");
        doAnswer(invocation -> null).when(domainNotificationPort).notifyError(anyString());
        userUseCase.createUser(user);
        verify(domainNotificationPort).notifyError(Constants.MSG_INVALID_ID);
    }

    @Test
    void createOwner_underage_notifyErrorCalled() {
        user.setBirthDate(LocalDate.now().minusYears(Constants.NUMERO_15));
        doAnswer(invocation -> null).when(domainNotificationPort).notifyError(anyString());
        Role role = new Role(1L, Constants.ROLE_OWNER, "Owner");
        when(rolePersistencePort.findByCode(Constants.ROLE_OWNER)).thenReturn(Optional.of(role));
        userUseCase.createOwner(user);
        verify(domainNotificationPort).notifyError(Constants.MSG_OF_LEGAL_AGE);
    }

    @Test
    void isOwner_returnsTrue() {
        String userName = "testuser";
        when(userPersistencePort.isRole(userName, Constants.ROLE_OWNER)).thenReturn(true);
        Boolean result = userUseCase.isRole(userName, Constants.ROLE_OWNER);
        assertEquals(true, result);
        verify(userPersistencePort).isRole(userName, Constants.ROLE_OWNER);
    }

    @Test
    void isOwner_returnsFalse() {
        String userName = "testuser";
        when(userPersistencePort.isRole(userName, Constants.ROLE_OWNER)).thenReturn(false);
        Boolean result = userUseCase.isRole(userName, Constants.ROLE_OWNER);
        assertEquals(false, result);
        verify(userPersistencePort).isRole(userName, Constants.ROLE_OWNER);
    }
}