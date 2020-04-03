package com.aot.groups.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aot.groups.models.User;
import com.aot.groups.repositories.UserRepository;

import antlr.collections.List;

@RestController
@RequestMapping("/users")
public class UserController {
	
	 	@Autowired
	 	private UserRepository userRepository;

	    @RequestMapping(value="/user", method = RequestMethod.GET)
	    public List getAllUsers(){
	        return (List) userRepository.findAll();
	    }

	    @RequestMapping(value = "/user", method = RequestMethod.POST)
	    public User create(@RequestBody User user){
	        return userRepository.save(user);
	    }

	    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	    public User findOne(@PathVariable long id){
	        return userRepository.findOne(id);
	    }

	    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
	    public User update(@PathVariable long id, @RequestBody User user){
	        user.setId(id);
	        return userRepository.save(user);
	    }

	    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	    public void delete(@PathVariable(value = "id") Long id){
	    	userRepository.delete(id);
	    }


}
