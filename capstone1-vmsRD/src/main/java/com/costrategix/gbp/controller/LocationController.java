package com.costrategix.gbp.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.costrategix.gbp.common.OrganizationLocations;
import com.costrategix.gbp.entity.Organizations;
import com.costrategix.gbp.entity.location_group;
import com.costrategix.gbp.entity.subLocations;
import com.costrategix.gbp.repository.OrganizationRepo;
import com.costrategix.gbp.repository.location_groupRepo;
import com.costrategix.gbp.repository.subLocationsRepo;
import com.costrategix.gbp.service.LocationService;




@RestController
public class LocationController {
	

	@Autowired
	private LocationService locservice;
	
	@GetMapping("/organization/{orgId}/locations")
	public Set<location_group> getGroupLocation(@PathVariable int orgId) {
		
		return locservice.getlocationgroups(orgId);
	}
	
	
	@GetMapping("/organization/{orgId}/location/{grpLocId}")
	public List<subLocations> getSubLocation(@PathVariable int grpLocId, @PathVariable int orgId) {
		return locservice.getsublocation(orgId, grpLocId);
	}

	@GetMapping("/location/{orgId}")
	public List<OrganizationLocations> getSubLocation(@PathVariable int orgId) {
		return locservice.getAll(orgId);
	}

}
