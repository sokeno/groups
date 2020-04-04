package com.aot.groups.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aot.groups.models.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
	
	
	Optional<Group> findByName(String name);


	Optional<Group> findByCreatedBy(Long created_by);

}
