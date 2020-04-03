package com.aot.groups.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Group {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "group_id")
	private Integer groupId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	

}
