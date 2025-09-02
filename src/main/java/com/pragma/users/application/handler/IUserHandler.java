package com.pragma.users.application.handler;

import com.pragma.users.application.dto.request.LoginRequestDto;
import com.pragma.users.application.dto.request.OwnerRequestDto;
import com.pragma.users.application.dto.response.TokenResponseDto;

public interface IUserHandler {
	void createOwner(OwnerRequestDto ownerRequestDto);

	Boolean isRole(String userName, String roler);

	TokenResponseDto auth(LoginRequestDto loginRequest);
}
