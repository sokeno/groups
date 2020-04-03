package com.aot.groups.controllers;

import java.util.Optional;

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
	    public Optional<User> findOne(@PathVariable int id){
	        return userRepository.findById(id);
	    }

	    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
	    public User update(@PathVariable int id, @RequestBody User user){
	    	User user1 = userRepository.findById(id).get();
//	        user1.setId(id);
	        return userRepository.save(user);
	    }

	    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	    public void delete(@PathVariable(value = "id") int id){
	    	userRepository.deleteById(id);
	    }


}
