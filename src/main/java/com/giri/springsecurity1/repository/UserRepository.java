package com.giri.springsecurity1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.giri.springsecurity1.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

}