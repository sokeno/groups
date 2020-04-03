package com.aot.groups.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aot.groups.models.Group;
import com.aot.groups.repositories.GroupRepository;

@RequestMapping("/api")
public class GroupController {
    @Autowired
    private GroupRepository groupRepository;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/groups")
    public List <Group> getAllGroups(){
    	
        return groupRepository.findAll();
    }

    @GetMapping("/groups/{id}")
    public ResponseEntity <Group> findGroup(@PathVariable Long id) {
    	
    	Optional<Group> groupData = groupRepository.findById(id);
        
        if (groupData.isPresent()) {
        	
            return new ResponseEntity<>(groupData.get(), HttpStatus.OK);
            
          } else {
        	  
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            
          }
    }

    @GetMapping("/groups/{name}")
    public ResponseEntity <Group> findByName(@PathVariable String name){
    	
    	Optional<Group> groupData = groupRepository.findByName(name);
    	
    	 if (groupData.isPresent()) {
         	
             return new ResponseEntity<>(groupData.get(), HttpStatus.OK);
             
           } else {
         	  
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
             
           }
//        return groupRepository.findByName(name);
    }
    
    @DeleteMapping("/groups/{id}")
    public ResponseEntity<Group> deleteById(@PathVariable("id") Long id){
    	
    	try {
    		
    		groupRepository.deleteById(id);
    		
    	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    	      
    	    } catch (Exception e) {
    	    	
    	      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    	      
    	    }
    }
    
    @PostMapping("/groups")
    public ResponseEntity<Group> createGroup(@RequestBody Group group) {
    	
      try {
    	  
    	  Group new_group = groupRepository
    			  
            .save(new Group(group.getName(),group.getDescription()));
    	  
        return new ResponseEntity<>(new_group, HttpStatus.CREATED);
        
      } catch (Exception e) {
    	  
        return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        
      }
    }
    
    
    @PutMapping("/groups/{id}")
    public ResponseEntity<Group> updateGroup(@PathVariable("id") long id, @RequestBody Group group) {
    	
      Optional<Group> groupData = groupRepository.findById(id);

      if (groupData.isPresent()) {
    	  
    	  Group new_group = groupData.get();
    	  
    	  new_group.setName(group.getName());
    	  
    	  new_group.setDescription(group.getDescription());
    	  
        return new ResponseEntity<>(groupRepository.save(new_group), HttpStatus.OK);
        
      } else {
    	  
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }



}
