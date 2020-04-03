package com.aot.groups.models;

import java.util.Collection;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

public class Privilege {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int privilege_id;
 
    private String name;
 
    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;

}
