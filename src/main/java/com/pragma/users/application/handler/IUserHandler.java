package com.pragma.users.application.handler;

import com.pragma.users.application.dto.request.OwnerRequestDto;

public interface IUserHandler {
	void createOwner(OwnerRequestDto ownerRequestDto);
}
