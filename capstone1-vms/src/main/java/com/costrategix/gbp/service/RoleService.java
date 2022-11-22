package com.costrategix.gbp.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.costrategix.gbp.entity.Roles;
import com.costrategix.gbp.repository.OrganizationRepo;
import com.costrategix.gbp.repository.RoleRepo;

@Service
public class RoleService {
	
	private static final Logger LOGGER = LogManager.getLogger(RoleService.class);
	
	@Autowired
    private RoleRepo repository;
    
	@Cacheable(value = "AllRoles")
    public Iterable<Roles> getroles()
    {	
    	LOGGER.info("Getting all the Roles");
    	return repository.findAll();
    }
    
    
	@Cacheable(key= "#id", value = "RolesByOrg")
    public List<Roles> getrolesbyorgid(int id){
    	
    	LOGGER.info("Fetching Roles for the organization id : ", id);
    	List<Roles> roles = repository.findRolesByOrganizationsId(id);
    	return roles;
    }
    
}
