package com.costrategix.gbp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RestController;

import com.costrategix.gbp.entity.Organizations;
import com.costrategix.gbp.entity.Roles;
import com.costrategix.gbp.repository.OrganizationRepo;
import com.costrategix.gbp.repository.RoleRepo;
import com.costrategix.gbp.service.RoleService;


@RestController
public class RolesController {
	
    @Autowired
    private RoleService roleservice;
    
   

    @GetMapping("/roles")
    public Iterable<Roles> getAllItems() {
    	
        return roleservice.getroles();
    }
	   
    @GetMapping("/organization/{orgid}/roles")
    public List<Roles> getallrolesbyorgid(@PathVariable int orgid) {
    	
        return roleservice.getrolesbyorgid(orgid);
    }
    
   
	  

}
