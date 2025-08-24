package com.pragma.users.application.handler.impl;

import org.springframework.stereotype.Service;

import com.pragma.users.application.dto.request.OwnerRequestDto;
import com.pragma.users.application.handler.IUserHandler;
import com.pragma.users.application.mapper.IUserRequestMapper;
import com.pragma.users.application.mapper.IUserResponseMapper;
import com.pragma.users.domain.api.IUserServicePort;
import com.pragma.users.domain.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserHandler implements IUserHandler {

	private final IUserServicePort userServicePort;
	private final IUserRequestMapper userRequestMapper;
	private final IUserResponseMapper userResponseMapper;

	@Override
	public void createOwner(final OwnerRequestDto ownerRequestDto) {
		User user = userRequestMapper.toUser(ownerRequestDto);
		userServicePort.createOwner(user);
	}
}
