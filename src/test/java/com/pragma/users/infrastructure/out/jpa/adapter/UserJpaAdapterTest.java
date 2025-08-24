package com.pragma.users.infrastructure.out.jpa.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.pragma.users.domain.model.User;
import com.pragma.users.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.users.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.users.infrastructure.out.jpa.repository.IUserRepository;

class UserJpaAdapterTest {

	@Mock
	private IUserRepository userRepository;
	@Mock
	private IUserEntityMapper userEntityMapper;
	@InjectMocks
	private UserJpaAdapter userJpaAdapter;

	private User user;
	private UserEntity userEntity;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		user = new User();
		user.setId(1L);
		user.setEmail("test@correo.com");
		userEntity = new UserEntity();
		userEntity.setId(1L);
		userEntity.setEmail("test@correo.com");
	}

	@Test
	void saveUser_ok() {
		when(userEntityMapper.toEntity(user)).thenReturn(userEntity);
		when(userRepository.save(userEntity)).thenReturn(userEntity);
		when(userEntityMapper.toUser(userEntity)).thenReturn(user);

		User result = userJpaAdapter.saveUser(user);
		assertEquals("test@correo.com", result.getEmail());
	}
}
