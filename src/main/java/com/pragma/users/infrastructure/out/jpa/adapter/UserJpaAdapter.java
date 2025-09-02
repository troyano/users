package com.pragma.users.infrastructure.out.jpa.adapter;

import com.pragma.users.domain.model.UserEmployee;
import org.springframework.stereotype.Component;

import com.pragma.users.domain.model.User;
import com.pragma.users.domain.model.UserAuth;
import com.pragma.users.domain.spi.IUserPersistencePort;
import com.pragma.users.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.users.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.users.infrastructure.out.jpa.repository.IUserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

	private final IUserRepository userRepository;
	private final IUserEntityMapper userEntityMapper;

	@Override
	public User saveUser(final User user) {
		UserEntity userEntity = userEntityMapper.toEntity(user);
		UserEntity savedEntity = userRepository.save(userEntity);
		return userEntityMapper.toUser(savedEntity);
	}

	@Override
	public Boolean isRole(String userName, String role) {
		return userRepository.existsByEmailAndRoleCode(userName, role);
	}

	@Override
	public UserAuth userByEmail(String userName) {
		UserEntity userEntity = userRepository.findByEmail(userName).orElse(null);
		return userEntityMapper.toUserAuth(userEntity);
	}

	@Override
	public void saveUserEmployee(UserEmployee user) {
		UserEntity userEntity = userEntityMapper.toEntity(user);
		userRepository.save(userEntity);
	}
	@Override
	public boolean userExist(String userName) {
		return userRepository.existsByEmail(userName);
	}
}
