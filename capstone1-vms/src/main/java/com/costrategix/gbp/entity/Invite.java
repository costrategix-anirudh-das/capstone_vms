package com.costrategix.gbp.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="invite")
public class Invite {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name="emailAddress")
	private String emailAddress;
	
	
	@Column(name="inviteToken",unique=true)
	private String inviteToken;
	
	@Column(name="expireDateTime")
	private LocalDateTime expireDateTime=LocalDateTime.now().plusDays(7);
	
	@Column(name="organizationId")
	private int organizationId;
	
	@Column(name="userId")
	private int userId;
	
	public Invite() {
		
	}

	public Invite(String emailAddress, String inviteToken,  int organizationId,
			int userId) {

		this.emailAddress = emailAddress;
		this.inviteToken = inviteToken;
		this.organizationId = organizationId;
		this.userId = userId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getInviteToken() {
		return inviteToken;
	}

	public void setInviteToken(String inviteToken) {
		this.inviteToken = inviteToken;
	}

	public LocalDateTime getExpireDateTime() {
		return expireDateTime;
	}

	public void setExpireDateTime(LocalDateTime expireDateTime) {
		this.expireDateTime = expireDateTime;
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
	
	
}
