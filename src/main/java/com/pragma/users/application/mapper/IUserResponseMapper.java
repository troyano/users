package com.pragma.users.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.pragma.users.application.dto.response.UserResponseDto;
import com.pragma.users.domain.model.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserResponseMapper {
	@Mapping(target = "role", source = "role.name")
	UserResponseDto toResponse(User user);
}
