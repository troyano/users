package com.pragma.users.application.mapper;

import com.pragma.users.application.dto.request.ClientRequestDto;
import com.pragma.users.application.dto.request.EmployeeRequestDto;
import com.pragma.users.domain.model.UserClient;
import com.pragma.users.domain.model.UserEmployee;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.pragma.users.application.dto.request.LoginRequestDto;
import com.pragma.users.application.dto.request.OwnerRequestDto;
import com.pragma.users.domain.model.Login;
import com.pragma.users.domain.model.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserRequestMapper {
	User toUser(OwnerRequestDto ownerRequestDto);

	Login toLoginUser(LoginRequestDto loginRequest);

	UserEmployee toUserEmployee(EmployeeRequestDto employeeRequestDto);

	UserClient toUserClient(ClientRequestDto clientRequestDto);
}
