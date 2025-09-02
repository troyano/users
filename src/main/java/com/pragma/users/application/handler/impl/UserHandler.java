package com.pragma.users.application.handler.impl;

import com.pragma.users.application.dto.request.EmployeeRequestDto;
import com.pragma.users.domain.api.IUserEmployeeServicePort;
import com.pragma.users.domain.model.UserEmployee;
import org.springframework.stereotype.Service;

import com.pragma.users.application.dto.request.LoginRequestDto;
import com.pragma.users.application.dto.request.OwnerRequestDto;
import com.pragma.users.application.dto.response.TokenResponseDto;
import com.pragma.users.application.handler.IUserHandler;
import com.pragma.users.application.mapper.IAuthResponseMapper;
import com.pragma.users.application.mapper.IUserRequestMapper;
import com.pragma.users.domain.api.IAuthServicePort;
import com.pragma.users.domain.api.IUserServicePort;
import com.pragma.users.domain.model.Login;
import com.pragma.users.domain.model.TokenResponse;
import com.pragma.users.domain.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserHandler implements IUserHandler {

	private final IUserServicePort userServicePort;
	private final IUserEmployeeServicePort userEmployeeServicePort;
	private final IAuthServicePort authService;

	private final IUserRequestMapper userRequestMapper;
	private final IAuthResponseMapper authResponseMapper;

	@Override
	public void createOwner(final OwnerRequestDto ownerRequestDto) {
		User user = userRequestMapper.toUser(ownerRequestDto);
		userServicePort.createOwner(user);
	}

	@Override
	public Boolean isRole(final String userName, final String role) {
		return userServicePort.isRole(userName, role);
	}

	@Override
	public TokenResponseDto auth(LoginRequestDto loginRequestDto) {
		Login loginRequest = userRequestMapper.toLoginUser(loginRequestDto);
		TokenResponse tokenResponse = authService.auth(loginRequest);
		return authResponseMapper.toResponse(tokenResponse);
	}

	@Override
	public void createEmployee(EmployeeRequestDto employeeRequestDto) {
		UserEmployee user = userRequestMapper.toUserEmployee(employeeRequestDto);
		userEmployeeServicePort.createEmployee(user);
	}
}
