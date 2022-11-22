package com.costrategix.gbp.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.costrategix.gbp.ApplicationTests;
import com.costrategix.gbp.entity.Organizations;
import com.costrategix.gbp.entity.Roles;
import com.costrategix.gbp.repository.OrganizationRepo;
import com.costrategix.gbp.repository.PasswordResetRepo;
import com.costrategix.gbp.repository.RoleRepo;
import com.costrategix.gbp.repository.UserRepo;
import com.costrategix.gbp.service.RoleService;


public class RolesTests extends ApplicationTests {

	@Autowired
	private WebApplicationContext web;
	
	private MockMvc mockMvc;

	@MockBean
	private RoleService service;
	
	
	@Before 
	public void setup()
	{
		mockMvc = MockMvcBuilders.webAppContextSetup(web).build();
	}

	@Test
	public void Allroles() throws Exception {

		when(service.getroles()).thenReturn(Arrays.asList(new Roles(1, "role1"), new Roles(2, "role2")));

		RequestBuilder request = MockMvcRequestBuilders.get("/roles").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(request).andExpect(status().isOk())
				.andExpect(content().json("[{\"id\":1,\"role_name\":\"role1\"},{\"id\":2,\"role_name\":\"role2\"}]")).andReturn();
	}



	
	  @Test 
	  public void org_based_roles() throws Exception {
	  
	  List<Roles> value = Arrays.asList(new Roles(1,"role1")); 
	  //Organizations orgs= new Organizations(10,"apolo"); //orgs.AddRole((Roles) value);
	  when(service.getrolesbyorgid(10)).thenReturn(value);
	  
	  
	  RequestBuilder request = MockMvcRequestBuilders .get("/organization/{org_id}/roles",10)
	  .accept(MediaType.APPLICATION_JSON);
	  
	  MvcResult result = mockMvc.perform(request).andExpect(content().json(
	  "[{\"id\":1,\"role_name\":\"role1\"}]")) .andReturn();
	  
	  }
	 

}
