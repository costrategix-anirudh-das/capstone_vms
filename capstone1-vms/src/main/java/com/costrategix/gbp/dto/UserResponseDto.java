package com.costrategix.gbp.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.costrategix.gbp.entity.Organizations;
import com.costrategix.gbp.entity.Roles;
import com.costrategix.gbp.entity.UserAccountStatus;
import com.costrategix.gbp.entity.location_group;
import com.costrategix.gbp.entity.subLocations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class UserResponseDto implements Serializable {
	
	private static final long serialVersionUID = 3L;
	
	private int id;
	
	private String FirstName;
	
	private String LastName;
	
	private String emailAdress;
	
	private String JobTitle;
	
	private String created_at;
	
	private Organizations org;
	
	//private List<Integer> groupLocationId;
	
	private UserAccountStatus status;
	
	private Set<Roles> roles;	
	
	private String PhoneNumber;
	
	Set<location_group> userLocationgroup;
	
	private Set<subLocations> usersubLocationgroup;



	public UserResponseDto(int id, String firstName, String lastName, String emailAdress, String jobTitle, String created_at,
			Organizations org, UserAccountStatus status, Set<Roles> roles, String phoneNumber,
			Set<location_group> userLocationgroup, Set<subLocations> usersubLocationgroup) {
		super();
		this.id = id;
		FirstName = firstName;
		LastName = lastName;
		this.emailAdress = emailAdress;
		JobTitle = jobTitle;
		this.created_at = created_at;
		this.org = org;
		this.status = status;
		this.roles = roles;
		PhoneNumber = phoneNumber;
		this.userLocationgroup = userLocationgroup;
		this.usersubLocationgroup = usersubLocationgroup;
	}

	public UserResponseDto() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getEmailAdress() {
		return emailAdress;
	}

	public void setEmailAdress(String emailAdress) {
		this.emailAdress = emailAdress;
	}

	public String getJobTitle() {
		return JobTitle;
	}

	public void setJobTitle(String jobTitle) {
		JobTitle = jobTitle;
	}



	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public Organizations getOrg() {
		return org;
	}

	public void setOrg(Organizations org) {
		this.org = org;
	}

	public UserAccountStatus getStatus() {
		return status;
	}

	public void setStatus(UserAccountStatus status) {
		this.status = status;
	}

	public Set<Roles> getRoles() {
		return roles;
	}

	public void setRoles(Set<Roles> roles) {
		this.roles = roles;
	}

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}

	public Set<location_group> getUserLocationgroup() {
		return userLocationgroup;
	}

	public void setUserLocationgroup(Set<location_group> userLocationgroup) {
		this.userLocationgroup = userLocationgroup;
	}

	public Set<subLocations> getUsersubLocationgroup() {
		return usersubLocationgroup;
	}

	public void setUsersubLocationgroup(Set<subLocations> usersubLocationgroup) {
		this.usersubLocationgroup = usersubLocationgroup;
	}
	
	
}
