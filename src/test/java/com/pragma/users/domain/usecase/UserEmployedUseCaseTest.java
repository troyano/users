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
import com.pragma.users.domain.model.UserEmployee;
import com.pragma.users.domain.spi.IPasswordEncoderPort;
import com.pragma.users.domain.spi.IRolePersistencePort;
import com.pragma.users.domain.spi.IUserPersistencePort;
import com.pragma.users.domain.util.Constants;
import com.pragma.users.infrastructure.exception.NoDataFoundException;

class UserEmployedUseCaseTest {
    @Mock private IUserPersistencePort userPersistencePort;
    @Mock private IRolePersistencePort rolePersistencePort;
    @Mock private IPasswordEncoderPort passwordEncoderPort;

    @InjectMocks private UserEmployeeUseCase userEmployeeUseCase;

    private UserEmployee userEmployee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userEmployee = new UserEmployee();
        userEmployee.setEmail("employee@test.com");
        userEmployee.setPhone("+573005698325");
        userEmployee.setIdentityDocument("987654321");
        userEmployee.setPassword("secret");
        userEmployeeUseCase = new UserEmployeeUseCase(userPersistencePort, rolePersistencePort, passwordEncoderPort);
    }

    @Test
    void createEmployee_ok() {
        when(userPersistencePort.userExist(userEmployee.getEmail())).thenReturn(false);
        Role role = new Role(2L, Constants.ROLE_EMPLOYEE, "Employee");
        when(rolePersistencePort.findByCode(Constants.ROLE_EMPLOYEE)).thenReturn(Optional.of(role));
        when(passwordEncoderPort.encode("secret")).thenReturn("encoded");

        userEmployeeUseCase.createEmployee(userEmployee);

        assertEquals("encoded", userEmployee.getPassword());
        assertEquals(role, userEmployee.getRole());
        verify(userPersistencePort).saveUserEmployee(userEmployee);
    }

    @Test
    void createEmployee_invalidEmail() {
        userEmployee.setEmail("invalid");
        when(userPersistencePort.userExist(userEmployee.getEmail())).thenReturn(false);
        ValidationException exception = assertThrows(ValidationException.class, () -> userEmployeeUseCase.createEmployee(userEmployee));
        assertEquals(Constants.MSG_INVALID_EMAIL_FORMAT, exception.getMessage());
    }

    @Test
    void createEmployee_userAlreadyExists() {
        when(userPersistencePort.userExist(userEmployee.getEmail())).thenReturn(true);
        ValidationDatExistException exception = assertThrows(ValidationDatExistException.class, () -> userEmployeeUseCase.createEmployee(userEmployee));
        assertEquals(Constants.MSG_USER_EMPLOYEE_ALREADY_EXISTS, exception.getMessage());
    }

    @Test
    void createEmployee_invalidPhone() {
        userEmployee.setPhone("invalid");
        when(userPersistencePort.userExist(userEmployee.getEmail())).thenReturn(false);
        ValidationException exception = assertThrows(ValidationException.class, () -> userEmployeeUseCase.createEmployee(userEmployee));
        assertEquals(Constants.MSG_CELL_PHONE_REGEX, exception.getMessage());
    }

    @Test
    void createEmployee_invalidId() {
        userEmployee.setIdentityDocument("invalid_id");
        when(userPersistencePort.userExist(userEmployee.getEmail())).thenReturn(false);
        ValidationException exception = assertThrows(ValidationException.class, () -> userEmployeeUseCase.createEmployee(userEmployee));
        assertEquals(Constants.MSG_ID_NUMERIC, exception.getMessage());
    }

    @Test
    void createEmployee_noRole() {
        when(userPersistencePort.userExist(userEmployee.getEmail())).thenReturn(false);
        when(rolePersistencePort.findByCode(Constants.ROLE_EMPLOYEE)).thenReturn(Optional.empty());
        NoDataFoundException exception = assertThrows(NoDataFoundException.class, () -> userEmployeeUseCase.createEmployee(userEmployee));
        assertEquals(Constants.MSG_ROLE_EMPLOYEE_DOES_NOT_EXIST, exception.getMessage());
    }
}
