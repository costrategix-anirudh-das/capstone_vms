package com.costrategix.gbp.entity;


import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="location_group")
public class location_group implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int loc_id;
	
	@Column(name="state_name")
	private String statename;

	public location_group() {
		
	}
	
	public location_group(int loc_id, String statename) {
		super();
		this.loc_id = loc_id;
		this.statename = statename;
	}

	public int getLoc_id() {
		return loc_id;
	}

	public void setLoc_id(int loc_id) {
		this.loc_id = loc_id;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}
}
