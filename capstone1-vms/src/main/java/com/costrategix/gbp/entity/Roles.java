package com.costrategix.gbp.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "roles")
public class Roles implements Serializable{
    
	
	private static final long serialVersionUID = 1L;
	public Roles() {
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="role_id")
    private int id;

    @Column(name="role_name")
    private String role_name;
    
    @ManyToMany(mappedBy = "roles", cascade= {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Organizations> organizations = new HashSet<>();

	public int getId() {
		return id;
	}
	
	
	@ManyToMany(mappedBy = "roless",cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnore
	List<User> users = new ArrayList<>();

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Roles(int id, String role_name) {

		this.id = id;
		this.role_name = role_name;
		
	}

	@Override
	public String toString() {
		return "Roles [id=" + id + ", role_name=" + role_name + ", organizations=" + organizations + "]";
	}

	public Set<Organizations> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(Set<Organizations> organizations) {
		this.organizations = organizations;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
}

