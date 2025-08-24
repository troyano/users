package com.pragma.users.infrastructure.out.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pragma.users.infrastructure.out.jpa.entity.UserEntity;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {

	Boolean existsByEmailAndRoleCode(String email, String roleCode);

}