package com.pragma.users.domain.api;

import com.pragma.users.domain.model.Login;
import com.pragma.users.domain.model.TokenResponse;

public interface IAuthServicePort {

	TokenResponse auth(Login login);
}
