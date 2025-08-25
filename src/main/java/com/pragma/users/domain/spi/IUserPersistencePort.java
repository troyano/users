package com.pragma.users.domain.spi;

import com.pragma.users.domain.model.User;

public interface IUserPersistencePort {
	User saveUser(User user);

	Boolean isRole(String userName, String role);
}
