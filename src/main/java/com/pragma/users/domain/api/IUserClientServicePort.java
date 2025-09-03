package com.pragma.users.domain.api;

import com.pragma.users.domain.model.UserClient;

public interface IUserClientServicePort {
	void createClient(UserClient user);
}
