package com.pragma.users.domain.spi;

import java.util.Optional;

import com.pragma.users.domain.model.Role;

public interface IRolePersistencePort {
	Optional<Role> findByCode(String code);
}
