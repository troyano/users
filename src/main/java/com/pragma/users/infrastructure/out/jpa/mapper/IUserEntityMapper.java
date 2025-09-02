package com.pragma.users.infrastructure.out.jpa.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.pragma.users.domain.model.User;
import com.pragma.users.domain.model.UserAuth;
import com.pragma.users.infrastructure.out.jpa.entity.UserEntity;

@Mapper(componentModel = "spring", uses = { IRoleEntityMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserEntityMapper {
	User toUser(UserEntity userEntity);

	@Mapping(target = "role", source = "role.code")
	UserAuth toUserAuth(UserEntity userEntity);

	UserEntity toEntity(User user);
}
