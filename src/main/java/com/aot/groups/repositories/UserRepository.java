package com.aot.groups.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aot.groups.models.User;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByName(String username);
}