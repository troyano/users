package com.pragma.users.domain.api;

import com.pragma.users.domain.model.UserEmployee;

public interface IUserEmployeeServicePort {
	void createEmployee(UserEmployee user);
}
