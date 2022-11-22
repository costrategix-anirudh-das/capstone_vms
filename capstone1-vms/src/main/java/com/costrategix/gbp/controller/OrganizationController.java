package com.costrategix.gbp.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.costrategix.gbp.entity.Organizations;
import com.costrategix.gbp.repository.OrganizationRepo;
import com.costrategix.gbp.service.OrganizationService;

@RestController
public class OrganizationController {
	
	@Autowired
	private OrganizationService organizationService;
		@GetMapping("/orgs")
	    public List<Organizations> getAllOrganizations() {
	        return organizationService.getAllOrgss();
	    }
		
		@GetMapping("/orgs/{orgId}")
		public Organizations getOrganization(@PathVariable int orgId) {
			return organizationService.getOrg(orgId);
		}
}
