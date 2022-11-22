package com.costrategix.gbp.entity;

import java.util.List;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToMany;

import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

import javax.persistence.Table;

import org.springframework.data.redis.core.RedisHash;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="organizations")
public class Organizations implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="org_id")
	private int id;
	
	@Column(name="org_name")
	private String name;
	
	@ManyToMany(cascade= {CascadeType.ALL}, fetch = FetchType.EAGER)
	@JoinTable(name="organization_sublocation", joinColumns= { @JoinColumn(name="org_id")},
	inverseJoinColumns = {@JoinColumn(name="subloc_id")})
	@JsonIgnore
	Set<subLocations> sublocations = new HashSet<>();

	@ManyToMany(cascade= {CascadeType.ALL}, fetch = FetchType.EAGER)
	@JoinTable(name="organization_role", joinColumns= { @JoinColumn(name="org_id")},
	inverseJoinColumns = {@JoinColumn(name="role_id")})
	@JsonIgnore
	Set<Roles> roles = new HashSet<>();
	


	public Organizations() {}

	public Organizations(int id, String name) {
		this.id = id;
		this.name = name;
	}

	

	public Set<subLocations> getSublocations() {
		return sublocations;
	}

	public void setSublocations(Set<subLocations> sublocations) {
		this.sublocations = sublocations;
	}

	public Set<Roles> getRoles() {
		return roles;
	}

	public void setRoles(Set<Roles> roles) {
		this.roles = roles;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void AddRole(Roles role)
	{
		this.roles.add(role);
		role.getOrganizations().add(this);
	}
}
