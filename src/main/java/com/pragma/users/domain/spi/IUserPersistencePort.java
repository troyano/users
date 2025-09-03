package com.pragma.users.domain.spi;

import com.pragma.users.domain.model.User;
import com.pragma.users.domain.model.UserAuth;
import com.pragma.users.domain.model.UserClient;
import com.pragma.users.domain.model.UserEmployee;

public interface IUserPersistencePort {
	User saveUser(User user);

	Boolean isRole(String userName, String role);

	UserAuth userByEmail(String userName);

	void saveUserEmployee(UserEmployee user);

	boolean userExist(String userName);

	void saveClient(UserClient user);
}
