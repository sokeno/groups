package com.aot.groups.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.aot.groups.models.Group;
import com.aot.groups.repositories.GroupRepository;

public class GroupController {
    @Autowired
    private GroupRepository groupRepository;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/groups")
    public List <Group> getAllGroups(){
        return groupRepository.findAll();
    }

    @GetMapping("/groups/{id}")
    public Optional<Group> findGroup(@PathVariable int id) {
        return groupRepository.findById(id);
    }

    @GetMapping("/name/{name}")
    public Optional<Group> findByName(@PathVariable String name){
        return groupRepository.findByName(name);
    }

}
