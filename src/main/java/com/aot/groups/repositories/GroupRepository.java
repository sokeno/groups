package com.aot.groups.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aot.groups.models.Group;

public interface GroupRepository extends JpaRepository<Group, Integer> {

}
