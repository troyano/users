package com.pragma.users.infrastructure.out.jpa.adapter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.pragma.users.domain.model.Role;
import com.pragma.users.domain.spi.IRolePersistencePort;
import com.pragma.users.infrastructure.out.jpa.mapper.IRoleEntityMapper;
import com.pragma.users.infrastructure.out.jpa.repository.IRoleRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RoleJpaAdapter implements IRolePersistencePort {

    private final IRoleRepository rolRepository;
    private final IRoleEntityMapper rolEntityMapper;

    @Override
    public Optional<Role> findByCode(final String code) {
        return rolRepository.findByCode(code)
                .map(rolEntityMapper::toRole);
    }
}
