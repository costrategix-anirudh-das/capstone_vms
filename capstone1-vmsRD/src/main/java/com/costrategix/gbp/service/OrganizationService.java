package com.costrategix.gbp.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.cache.annotation.CacheConfig;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.costrategix.gbp.Application;
import com.costrategix.gbp.entity.Organizations;
import com.costrategix.gbp.repository.OrganizationRepo;

@Service
public class OrganizationService {
	
	private static final Logger LOGGER = LogManager.getLogger(OrganizationService.class);
	
	Random random;
	
	@Autowired
	private OrganizationRepo repo;
	
	
	@Cacheable(value = "Allorganizations")
	 public List<Organizations> getAllOrgss(){
		 LOGGER.info("Fetching all Organization");
		 return repo.findAll();
	 }
	 
	 @Cacheable(key= "#orgId", value = "organizations")
	 public Organizations getOrg(int orgId) {
		 System.out.println("reaching DB");
			try {
				Optional<Organizations> org = repo.findById(orgId);
				LOGGER.info("Fetching Organization with id : ", orgId);
				return org.get();
			}
			catch(NoSuchElementException e) {
				LOGGER.warn("No Organization with id : ", orgId);
				return null;
			}
	 }
	 
}
