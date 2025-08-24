package com.pragma.users.infrastructure.out.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pragma.users.infrastructure.out.jpa.entity.RoleEntity;

public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {
	Optional<RoleEntity> findByCode(String code);
}
