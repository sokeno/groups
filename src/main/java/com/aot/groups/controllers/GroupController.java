package com.aot.groups.controllers;

import java.util.Collection;
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
import org.springframework.web.bind.annotation.RestController;

import com.aot.groups.models.Group;
import com.aot.groups.models.User;
import com.aot.groups.repositories.GroupRepository;
import com.aot.groups.repositories.UserRepository;
import com.aot.groups.security.CurrentUser;
import com.aot.groups.security.UserPrincipal;
import com.aot.groups.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api")
public class GroupController {
	
    @Autowired
    private GroupRepository groupRepository;
    
 	@Autowired
 	private UserRepository userRepository;

    
    @GetMapping("/groups")
    public ResponseEntity <List<Group>> getAllGroups(){
    	
        return new ResponseEntity<> (groupRepository.findAll(),HttpStatus.OK);
    }

    @GetMapping("/groups/{id}")
    public ResponseEntity <Group> findGroup(@PathVariable Long id) {
    	
    	Optional<Group> groupData = groupRepository.findById(id);
        
        if (groupData.isPresent()) {
        	
            return new ResponseEntity<>(groupData.get(), HttpStatus.OK);
            
          } else {
        	  
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
            
          }
    }

    @GetMapping("/groups/name/{name}")
    public ResponseEntity <Group> findByName(@PathVariable String name){
    	
    	Optional<Group> groupData = groupRepository.findByName(name);
    	
    	 if (groupData.isPresent()) {
         	
             return new ResponseEntity<>(groupData.get(), HttpStatus.OK);
             
           } else {
         	  
             return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
             
           }
    }
    
    @DeleteMapping("/groups/{id}")
    public ResponseEntity<Group> deleteById(@PathVariable("id") Long id){
    	
    	try {
    		
    		groupRepository.deleteById(id);
    		
    	      return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
    	      
    	    } catch (Exception e) {
    	    	
    	      return new ResponseEntity<>(null,HttpStatus.EXPECTATION_FAILED);
    	      
    	    }
    }
    
    @PostMapping("/groups")
    public ResponseEntity<Group> createGroup(@RequestBody Group group, @CurrentUser UserPrincipal userPrincipal) {
    	
      try {
    	  
    	  Group new_group = groupRepository
    			  
            .save(new Group(group.getName(),group.getDescription(), userPrincipal.getId()));
    	  
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
    	  
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
      }
    }

    @GetMapping("/groups/join/{id}")
    public ResponseEntity<String> joinGroup(@PathVariable("id") long id, @CurrentUser UserPrincipal userPrincipal) {
    	
      Optional<Group> groupData = groupRepository.findById(id);
      
      User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));


      if (groupData.isPresent()) {
    	  
    	  Group new_group = groupData.get();
    	  
    	  if(!new_group.getUsers().contains(user)) {
    		  
    	  new_group.getUsers().add(user);
    	  
    	  groupRepository.save(new_group);
    	  
        return new ResponseEntity<>("Successfully Joined Group", HttpStatus.OK);
    	  }
    	  return new ResponseEntity<>("You already joined the group",HttpStatus.CONFLICT);
      } else {
    	  
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
      }
    }
    
    @GetMapping("/groups/remove/{user_id}/{group_id}")
    public ResponseEntity<String> removeMemberFromGroup(@PathVariable("user_id") long user_id,@PathVariable("group_id") long group_id, @CurrentUser UserPrincipal userPrincipal) {
    	
    	Group groupData = groupRepository.findById(group_id).orElseThrow(() -> new ResourceNotFoundException("Group", "id", group_id));
    	
    	User user = userRepository.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User", "id", user_id));
    	
    	
    	if (!(userPrincipal.getId()== user_id) || groupData.getCreatedBy() == userPrincipal.getId()){
    		    		
    		
    		if(!groupData.getUsers().contains(user)) {

    			
    			return new ResponseEntity<>("User does not exist in this group", HttpStatus.NOT_FOUND);
    		}
    		
		
    		groupData.getUsers().remove(user);
			
			groupRepository.save(groupData);
    		return new ResponseEntity<>("User Successfully removed",HttpStatus.OK);
    	} else {
    		
    		return new ResponseEntity<>("You dont have permissions to remove the user from this group",HttpStatus.FORBIDDEN);
    	}
    }



}
