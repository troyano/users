package com.pragma.users.domain.api;

import com.pragma.users.domain.model.User;

public interface IUserServicePort {
	void createUser(User user);

	void createOwner(User user);

	Boolean isRole(String userName, String role);
}
