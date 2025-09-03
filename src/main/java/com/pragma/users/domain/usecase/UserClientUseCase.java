package com.pragma.users.domain.usecase;

import com.pragma.users.domain.api.IUserClientServicePort;
import com.pragma.users.domain.api.IUserEmployeeServicePort;
import com.pragma.users.domain.exception.ValidationUtils;
import com.pragma.users.domain.model.Role;
import com.pragma.users.domain.model.UserClient;
import com.pragma.users.domain.model.UserEmployee;
import com.pragma.users.domain.spi.IPasswordEncoderPort;
import com.pragma.users.domain.spi.IRolePersistencePort;
import com.pragma.users.domain.spi.IUserPersistencePort;
import com.pragma.users.domain.util.Constants;
import com.pragma.users.infrastructure.exception.NoDataFoundException;

public class UserClientUseCase implements IUserClientServicePort {

	private final IUserPersistencePort userPersistencePort;
	private final IRolePersistencePort rolePersistencePort;
	private final IPasswordEncoderPort passwordEncoderPort;

	public UserClientUseCase(final IUserPersistencePort userPersistencePort, final IRolePersistencePort rolePersistencePort,
                             final IPasswordEncoderPort passwordEncoderPort) {
		this.userPersistencePort = userPersistencePort;
		this.rolePersistencePort = rolePersistencePort;
		this.passwordEncoderPort = passwordEncoderPort;
	}

	private void validateBasicData(final UserClient user) {
		ValidationUtils.validateEmail(user.getEmail());
		ValidationUtils.dataExist(userPersistencePort.userExist(user.getEmail()), Constants.MSG_USER_CLIENT_ALREADY_EXISTS);
		ValidationUtils.validatePhone(user.getPhone());
		ValidationUtils.validateIdentityDocument(user.getIdentityDocument());
	}

	@Override
	public void createClient(final UserClient user) {
		validateBasicData(user);
		Role role = rolePersistencePort.findByCode(Constants.ROLE_CLIENT)
				.orElseThrow(() -> new NoDataFoundException(Constants.MSG_ROLE_EMPLOYEE_DOES_NOT_EXIST));
		user.setRoleId(role.getId());
		user.setPassword(passwordEncoderPort.encode(user.getPassword()));
		userPersistencePort.saveClient(user);
	}
}