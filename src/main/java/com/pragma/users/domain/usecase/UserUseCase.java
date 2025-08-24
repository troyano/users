package com.pragma.users.domain.usecase;

import java.time.LocalDate;
import java.time.Period;

import com.pragma.users.domain.api.IUserServicePort;
import com.pragma.users.domain.exception.DomainException;
import com.pragma.users.domain.model.Role;
import com.pragma.users.domain.model.User;
import com.pragma.users.domain.spi.IDomainNotificationPort;
import com.pragma.users.domain.spi.IPasswordEncoderPort;
import com.pragma.users.domain.spi.IRolePersistencePort;
import com.pragma.users.domain.spi.IUserPersistencePort;
import com.pragma.users.domain.util.Constants;

public class UserUseCase implements IUserServicePort {

	private final IUserPersistencePort userPersistencePort;
	private final IRolePersistencePort rolePersistencePort;
	private final IPasswordEncoderPort passwordEncoderPort;
	private final IDomainNotificationPort domainNotificationPort;

	public UserUseCase(final IUserPersistencePort userPersistencePort, final IRolePersistencePort rolePersistencePort,
			final IPasswordEncoderPort passwordEncoderPort, final IDomainNotificationPort domainNotificationPort) {
		this.userPersistencePort = userPersistencePort;
		this.rolePersistencePort = rolePersistencePort;
		this.passwordEncoderPort = passwordEncoderPort;
		this.domainNotificationPort = domainNotificationPort;
	}

	@Override
	public void createUser(final User user) {
		validateBasicData(user);
		userPersistencePort.saveUser(user);
	}

	private void validateBasicData(final User user) {
		if (!user.getEmail().matches(Constants.REGEX_EMAIL)) {
			domainNotificationPort.notifyError(Constants.MSG_INVALID_EMAIL);
		}
		if (!user.getPhone().matches(Constants.REGEX_CELL_PHONE)) {
			domainNotificationPort.notifyError(Constants.MSG_INVALID_CELL_PHONE);
		}
		if (!user.getIdentityDocument().matches(Constants.REGEX_ID)) {
			domainNotificationPort.notifyError(Constants.MSG_INVALID_ID);
		}
	}

	private void validateAdult(final LocalDate birthDate) {
		int age = Period.between(birthDate, LocalDate.now()).getYears();
		if (age < Constants.NUMERO_18) {
			domainNotificationPort.notifyError(Constants.MSG_OF_LEGAL_AGE);
		}
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
	public Boolean isOwner(final String userName) {
		return userPersistencePort.isOwner(userName);
	}
}