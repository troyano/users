package com.pragma.users.infrastructure.configuration;

import com.pragma.users.domain.api.IUserEmployeeServicePort;
import com.pragma.users.domain.usecase.UserEmployeeUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.pragma.users.domain.api.IAuthServicePort;
import com.pragma.users.domain.api.IUserServicePort;
import com.pragma.users.domain.spi.IJwtServicePort;
import com.pragma.users.domain.spi.IPasswordEncoderPort;
import com.pragma.users.domain.spi.IRolePersistencePort;
import com.pragma.users.domain.spi.IUserPersistencePort;
import com.pragma.users.domain.usecase.AuthUseCase;
import com.pragma.users.domain.usecase.UserUseCase;
import com.pragma.users.infrastructure.out.jpa.adapter.RoleJpaAdapter;
import com.pragma.users.infrastructure.out.jpa.adapter.UserJpaAdapter;
import com.pragma.users.infrastructure.out.jpa.mapper.IRoleEntityMapper;
import com.pragma.users.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.users.infrastructure.out.jpa.repository.IRoleRepository;
import com.pragma.users.infrastructure.out.jpa.repository.IUserRepository;
import com.pragma.users.infrastructure.out.security.JwtServiceAdapter;
import com.pragma.users.infrastructure.out.security.PasswordEncoderAdapter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

	private final IUserRepository userRepository;
	private final IUserEntityMapper userEntityMapper;

	private final IRoleRepository roleRepository;
	private final IRoleEntityMapper roleEntityMapper;

	@Bean
	public IUserPersistencePort userPersistencePort() {
		return new UserJpaAdapter(userRepository, userEntityMapper);
	}

	@Bean
	public IRolePersistencePort rolPersistencePort() {
		return new RoleJpaAdapter(roleRepository, roleEntityMapper);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public IPasswordEncoderPort passwordEncoderPort(PasswordEncoder passwordEncoder) {
		return new PasswordEncoderAdapter(passwordEncoder);
	}

	@Bean
	public IUserServicePort userServicePort() {
		return new UserUseCase(userPersistencePort(), rolPersistencePort(), passwordEncoderPort(passwordEncoder()));
	}

	@Bean
	public IUserEmployeeServicePort userEmployeeServicePort() {
		return new UserEmployeeUseCase(userPersistencePort(), rolPersistencePort(), passwordEncoderPort(passwordEncoder()));
	}

	@Bean
	public IJwtServicePort jwtServicePort(JwtService jwtService) {
		return new JwtServiceAdapter(jwtService);
	}

	@Bean
	public IAuthServicePort authServicePort(IJwtServicePort jwtServicePort) {
		return new AuthUseCase(userPersistencePort(), jwtServicePort, passwordEncoderPort(passwordEncoder()));
	}
	
}