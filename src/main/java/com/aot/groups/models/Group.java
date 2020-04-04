package com.aot.groups.models;

import java.util.List;

import javax.persistence.*;


@Entity
@Table(name = "groups")
public class Group {
	
	public Group() {
		super();
	}

	@Id
	@Column(name = "group_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "created_by")
	private Long created_by;
	

	@ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            })
	@JoinTable(
			name = "user_groups",
			joinColumns = @JoinColumn(name = "group_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id")
			)
	private List<User> users;
	
	
	
	public Group( String name, String description, Long created_by) {
		super();

		this.name = name;
		this.description = description;
		this.created_by = created_by;

	}



	public Group(List<User> users) {
		super();
		this.users = users;
	}



	
	
	public Long getCreated_by() {
		return created_by;
	}



	public void setCreated_by(Long created_by) {
		this.created_by = created_by;
	}



	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

}
