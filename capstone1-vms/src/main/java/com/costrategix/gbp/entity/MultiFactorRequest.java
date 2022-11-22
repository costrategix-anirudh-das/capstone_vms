package com.costrategix.gbp.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MultiFactorRequest")
public class MultiFactorRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="user_id")
	private int userId;
	
	@Column(name="code_value")
	private String codevalue;
	
	@Column(name="expired_date")
	private LocalDateTime expireddate = LocalDateTime.now().plusMinutes(30);
	
	@Column(name="isused")
	private boolean isUsed=false;
	
	@Column(name="created_at")
	private LocalDateTime createdtime = LocalDateTime.now();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getCodevalue() {
		return codevalue;
	}

	public void setCodevalue(String codevalue) {
		this.codevalue = codevalue;
	}

	public LocalDateTime getExpireddate() {
		return expireddate;
	}

	public void setExpireddate(LocalDateTime expireddate) {
		this.expireddate = expireddate;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	public LocalDateTime getCreatedtime() {
		return createdtime;
	}

	public void setCreatedtime(LocalDateTime createdtime) {
		this.createdtime = createdtime;
	}

	public MultiFactorRequest(int userId, String codevalue) {
		
		this.userId = userId;
		this.codevalue = codevalue;
		
	}

	public MultiFactorRequest() {
		super();
	}
	
	
}
