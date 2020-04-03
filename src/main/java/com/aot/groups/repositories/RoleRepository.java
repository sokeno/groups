package com.aot.groups.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aot.groups.models.ERole;
import com.aot.groups.models.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	Optional<Role> findByName(ERole name);

}
