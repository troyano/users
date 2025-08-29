package com.pragma.users.domain.usecase;

import java.time.LocalDate;
import java.time.Period;

import com.pragma.users.domain.api.IUserServicePort;
import com.pragma.users.domain.exception.DomainException;
import com.pragma.users.domain.exception.ValidationUtils;
import com.pragma.users.domain.model.Role;
import com.pragma.users.domain.model.User;
import com.pragma.users.domain.spi.IPasswordEncoderPort;
import com.pragma.users.domain.spi.IRolePersistencePort;
import com.pragma.users.domain.spi.IUserPersistencePort;
import com.pragma.users.domain.util.Constants;

public class UserUseCase implements IUserServicePort {

	private final IUserPersistencePort userPersistencePort;
	private final IRolePersistencePort rolePersistencePort;
	private final IPasswordEncoderPort passwordEncoderPort;

	public UserUseCase(final IUserPersistencePort userPersistencePort, final IRolePersistencePort rolePersistencePort,
			final IPasswordEncoderPort passwordEncoderPort) {
		this.userPersistencePort = userPersistencePort;
		this.rolePersistencePort = rolePersistencePort;
		this.passwordEncoderPort = passwordEncoderPort;
	}

	@Override
	public void createUser(final User user) {
		validateBasicData(user);
		userPersistencePort.saveUser(user);
	}

	private void validateBasicData(final User user) {
		ValidationUtils.validateEmail(user.getEmail());
		ValidationUtils.validatePhone(user.getPhone());
		ValidationUtils.validateIdentityDocument(user.getIdentityDocument());
	}

	private void validateAdult(final LocalDate birthDate) {
		int age = Period.between(birthDate, LocalDate.now()).getYears();
		ValidationUtils.validateAdultAge(age);
	}

	@Override
	public void createOwner(final User user) {
		validateBasicData(user);
		validateAdult(user.getBirthDate());

		Role roleOwner = rolePersistencePort.findByCode(Constants.ROLE_OWNER)
				.orElseThrow(() -> new DomainException(Constants.MSG_ROLE_OWNER_DOES_NOT_EXIST));

		user.setRole(roleOwner);
		user.setPassword(passwordEncoderPort.encode(user.getPassword()));

		userPersistencePort.saveUser(user);
	}

	@Override
	public Boolean isRole(final String userName, final String role) {
		return userPersistencePort.isRole(userName, role);
	}
}