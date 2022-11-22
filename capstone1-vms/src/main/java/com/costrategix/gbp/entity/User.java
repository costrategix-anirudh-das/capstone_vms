package com.costrategix.gbp.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity
@Table(name="User")
public class User implements Serializable {
	
	
	private static final long serialVersionUID = 4L;
	
	public User(int id, String firstName, String lastName, String emailAdress, int orgid, List<Integer> role_id,
            String jobTitle, String phoneNumber, Set<location_group> userLocationgroup,
            Set<subLocations> usersubLocationgroup) {
        super();
        this.id = id;
        FirstName = firstName;
        LastName = lastName;
        this.emailAdress = emailAdress;
        this.orgid = orgid;
        this.role_id = role_id;
        JobTitle = jobTitle;
        PhoneNumber = phoneNumber;
        this.userLocationgroup = userLocationgroup;
        this.usersubLocationgroup = usersubLocationgroup;
    }

	public User() {
		
	}

	public User(int id, String firstName, String lastName, String emailAdress, int orgid, int statusId,
			UserAccountStatus userAccountStatus, List<Integer> role_id, String jobTitle, String phoneNumber,
			LocalDateTime ldt, LocalDateTime updated, String createdBy, String updatedBy, Set<Roles> roless,
			int multiFactorNumber, LocalDateTime userStatusChangeDateTime, LocalDateTime lastLoginDateTime,
			int ssoSourceId, int ssoUserId, String securityHash, List<Integer> subLocationId,
			List<Integer> groupLocationId, Set<location_group> userLocationgroup,
			Set<subLocations> usersubLocationgroup) {
		super();
		this.id = id;
		FirstName = firstName;
		LastName = lastName;
		this.emailAdress = emailAdress;
		this.orgid = orgid;
		this.statusId = statusId;
		this.userAccountStatus = userAccountStatus;
		this.role_id = role_id;
		JobTitle = jobTitle;
		PhoneNumber = phoneNumber;
		this.ldt = ldt;
		this.updated = updated;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.roless = roless;
		MultiFactorNumber = multiFactorNumber;
		UserStatusChangeDateTime = userStatusChangeDateTime;
		LastLoginDateTime = lastLoginDateTime;
		SsoSourceId = ssoSourceId;
		SsoUserId = ssoUserId;
		SecurityHash = securityHash;
		this.subLocationId = subLocationId;
		this.groupLocationId = groupLocationId;
		this.userLocationgroup = userLocationgroup;
		this.usersubLocationgroup = usersubLocationgroup;
	}




	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="FirstName",nullable=false)	
	private String FirstName;
	
	@Column(name="LastName",nullable=false)
	private String LastName;

	@Column(name="EmailAdress",nullable=false,unique=true)
	private String emailAdress;
	
	@Column(name="org_id")
	private int orgid;


	@Column(name="statusId",nullable=false)
	@Transient
	private int statusId;

	@ManyToOne(cascade=CascadeType.ALL)
	private UserAccountStatus userAccountStatus;
	
	@ElementCollection
	@Transient
	private List<Integer> role_id;


	@Column(name="job_title")
	private String JobTitle;
	
	@Column(name="phone_number")
	private String PhoneNumber;
	
	@Column(name="createdAt")
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private LocalDateTime ldt = LocalDateTime.now();
	
	@Column(name="updatedAt")
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private LocalDateTime updated;
	
	@Column(name="createdBy")
	private String createdBy;
	
	@Column(name="updatedBy")
	private String updatedBy;
	
    @ManyToMany(cascade=CascadeType.PERSIST,  fetch = FetchType.EAGER)
    @JoinTable(name ="UserHasRoles",
	joinColumns = {@JoinColumn(name="user_id")},
	inverseJoinColumns = {@JoinColumn(name="role_id")})
	private Set<Roles> roless=new HashSet<>();
    
	@ColumnDefault("0")
	private int MultiFactorNumber=0;

	@Column(name="UserStatusChangeDateTime")
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private LocalDateTime UserStatusChangeDateTime=LocalDateTime.now();
	
	@Column(name="LastLoginDateTime")
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private LocalDateTime LastLoginDateTime=LocalDateTime.now();

	
	@ColumnDefault("0")
	private int SsoSourceId;
	
	@ColumnDefault("0")
	private int SsoUserId;
	
	@Column(name="security_hash")
	private String SecurityHash=null;

	
	@Column
	@Transient
	private List<Integer> subLocationId;
	
	@Transient
	@Column
	private List<Integer> groupLocationId;

    @ManyToMany(cascade=CascadeType.PERSIST,  fetch = FetchType.EAGER)
    @JoinTable(name ="UserLocGroup",
	joinColumns = {@JoinColumn(name="user_id")},
	inverseJoinColumns = {@JoinColumn(name="grp_location_id")})
	private Set<location_group> userLocationgroup =new HashSet<>();
	
    @ManyToMany(cascade=CascadeType.PERSIST,  fetch = FetchType.EAGER)
    @JoinTable(name ="UserSubLocGroup",
	joinColumns = {@JoinColumn(name="user_id")},
	inverseJoinColumns = {@JoinColumn(name="sub_location_id")})
	private Set<subLocations> usersubLocationgroup =new HashSet<>();
	
	
	
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

	public List<Integer> getSubLocationId() {
		return subLocationId;
	}

	public void setSubLocationId(List<Integer> subLocationId) {
		this.subLocationId = subLocationId;
	}

	public List<Integer> getGroupLocationId() {
		return groupLocationId;
	}

	public void setGroupLocationId(List<Integer> groupLocationId) {
		this.groupLocationId = groupLocationId;
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



	public int getOrgid() {
		return orgid;
	}



	public void setOrgid(int orgid) {
		this.orgid = orgid;
	}



	public int getStatusId() {
		return statusId;
	}



	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}



	public UserAccountStatus getUserAccountStatus() {
		return userAccountStatus;
	}



	public void setUserAccountStatus(UserAccountStatus userAccountStatus) {
		this.userAccountStatus = userAccountStatus;
	}



	public List<Integer> getRole_id() {
		return role_id;
	}



	public void setRole_id(List<Integer> role_id) {
		this.role_id = role_id;
	}

	public LocalDateTime getUserStatusChangeDateTime() {
		return UserStatusChangeDateTime;
	}

	public void setUserStatusChangeDateTime(LocalDateTime userStatusChangeDateTime) {
		UserStatusChangeDateTime = userStatusChangeDateTime;
	}

	public LocalDateTime getLastLoginDateTime() {
		return LastLoginDateTime;
	}

	public void setLastLoginDateTime(LocalDateTime lastLoginDateTime) {
		LastLoginDateTime = lastLoginDateTime;
	}

	public String getJobTitle() {
		return JobTitle;
	}



	public void setJobTitle(String jobTitle) {
		JobTitle = jobTitle;
	}



	public String getPhoneNumber() {
		return PhoneNumber;
	}



	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}



	public LocalDateTime getLdt() {
		return ldt;
	}



	public void setLdt(LocalDateTime ldt) {
		this.ldt = ldt;
	}



	public LocalDateTime getUpdated() {
		return updated;
	}



	public void setUpdated(LocalDateTime updated) {
		this.updated = updated;
	}



	public String getCreatedBy() {
		return createdBy;
	}



	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}



	public String getUpdatedBy() {
		return updatedBy;
	}



	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}


	public Set<Roles> getRoless() {
		return roless;
	}

	public void setRoless(Set<Roles> roless) {
		this.roless = roless;
	}

	public int getMultiFactorNumber() {
		return MultiFactorNumber;
	}



	public void setMultiFactorNumber(int multiFactorNumber) {
		MultiFactorNumber = multiFactorNumber;
	}





	public int getSsoSourceId() {
		return SsoSourceId;
	}



	public void setSsoSourceId(int ssoSourceId) {
		SsoSourceId = ssoSourceId;
	}



	public int getSsoUserId() {
		return SsoUserId;
	}



	public void setSsoUserId(int ssoUserId) {
		SsoUserId = ssoUserId;
	}



	public String getSecurityHash() {
		return SecurityHash;
	}



	public void setSecurityHash(String securityHash) {
		SecurityHash = securityHash;
	}
	
}
