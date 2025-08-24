package com.pragma.users.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.pragma.users.application.dto.request.OwnerRequestDto;
import com.pragma.users.domain.model.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserRequestMapper {
	User toUser(OwnerRequestDto ownerRequestDto);
}
