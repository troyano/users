package com.pragma.users.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.pragma.users.application.dto.response.TokenResponseDto;
import com.pragma.users.domain.model.TokenResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IAuthResponseMapper {

	TokenResponseDto toResponse(TokenResponse token);
}
