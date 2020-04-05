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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aot.groups.exception.ResourceNotFoundException;
import com.aot.groups.models.Group;
import com.aot.groups.models.User;
import com.aot.groups.repositories.UserRepository;
import com.aot.groups.security.CurrentUser;
import com.aot.groups.security.UserPrincipal;


@RestController
@RequestMapping("/users")
public class UserController {
	
	 	@Autowired
	 	private UserRepository userRepository;
	 	
	    @GetMapping("/user/me")
	    @PreAuthorize("hasRole('USER')")
	    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
	        return userRepository.findById(userPrincipal.getId())
	                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
	    }
	    
	    @GetMapping("/user/me/groups")
	    @PreAuthorize("hasRole('USER')")
	    public ResponseEntity<List<Group>> getCurrentUserGroups(@CurrentUser UserPrincipal userPrincipal) {
	    	
	    	List<Group> userData = userRepository.findById(userPrincipal.getId()).get().getGroups();
	    	
	    	if(!userData.isEmpty()) {
	    		
	    	return new ResponseEntity<>(userData, HttpStatus.OK);
	    	
	    	}
	    	
	    	return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
	    }


	    @GetMapping(value="/user")
	    public List getAllUsers(){
	        return (List) userRepository.findAll();
	    }

	    @PostMapping(value = "/user")
	    public User create(@RequestBody User user){
	        return userRepository.save(user);
	    }

	    @GetMapping(value = "/user/{id}")
	    public User findOne(@PathVariable Long id){
	        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
	    }

	    @PutMapping(value = "/user/{id}")
	    public User update(@PathVariable Long id, @RequestBody User user){
	    	User user1 = userRepository.findById(id).get();
//	        user1.setId(id);
	        return userRepository.save(user);
	    }

	    @DeleteMapping(value = "/user/{id}")
	    public void delete(@PathVariable(value = "id") Long id){
	    	userRepository.deleteById(id);
	    }


}
