package com.pragma.users.infrastructure.out.jpa.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.pragma.users.domain.model.Role;
import com.pragma.users.infrastructure.out.jpa.entity.RoleEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IRoleEntityMapper {
	Role toRole(RoleEntity roleEntity);

	RoleEntity toEntity(Role role);
}
