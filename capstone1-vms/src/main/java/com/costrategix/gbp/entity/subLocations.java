package com.costrategix.gbp.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="sublocation")
public class subLocations implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public subLocations(){
		
	}
	

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sub_loc_id;
	
	@Column(name="sub_loc_name")
	private String sub_loc_name;
	
	@ManyToOne
	@JoinColumn(name="grp_loc_id")
	@JsonIgnore
	private location_group lg;
	

    @ManyToMany(mappedBy = "sublocations", cascade= {CascadeType.ALL})
    @JsonIgnore
    private Set<Organizations> organizations = new HashSet<>();

    
 

	public subLocations(int sub_loc_id, String sub_loc_name, location_group lg, Set<Organizations> organizations) {
		this.sub_loc_id = sub_loc_id;
		this.sub_loc_name = sub_loc_name;
		this.lg = lg;
		this.organizations = organizations;
	}
	
	


	public Set<Organizations> getOrganizations() {
		return organizations;
	}




	public void setOrganizations(Set<Organizations> organizations) {
		this.organizations = organizations;
	}




	public int getSub_loc_id() {
		return sub_loc_id;
	}



	public void setSub_loc_id(int sub_loc_id) {
		this.sub_loc_id = sub_loc_id;
	}



	public String getSub_loc_name() {
		return sub_loc_name;
	}



	public void setSub_loc_name(String sub_loc_name) {
		this.sub_loc_name = sub_loc_name;
	}

	public location_group getLg() {
		return lg;
	}

	public void setLg(location_group lg) {
		this.lg = lg;
	}

}
