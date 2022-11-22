package com.costrategix.gbp.common;

import java.io.Serializable;
import java.util.List;

import com.costrategix.gbp.entity.subLocations;

public class OrganizationLocations implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String locationName;
	private List<subLocations> sublocations;
	


public OrganizationLocations(int id, String locationName, List<subLocations> sublocations) {
		super();
		this.id = id;
		this.locationName = locationName;
		this.sublocations = sublocations;
	}
	
	public List<subLocations> getSublocations() {
		return sublocations;
	}
	public void setSublocations(List<subLocations> sublocations) {
		this.sublocations = sublocations;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
}