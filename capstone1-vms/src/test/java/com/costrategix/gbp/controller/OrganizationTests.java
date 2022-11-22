package com.costrategix.gbp.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.costrategix.gbp.ApplicationTests;
import com.costrategix.gbp.entity.Organizations;
import com.costrategix.gbp.repository.OrganizationRepo;
import com.costrategix.gbp.service.OrganizationService;



public class OrganizationTests extends ApplicationTests{
    
    
    @Autowired
    private WebApplicationContext web;
    
    private MockMvc mockMvc;
    
    @MockBean
    private OrganizationService service;
    


    @Before 
    public void setup()
    {
        mockMvc = MockMvcBuilders.webAppContextSetup(web).build();
    }
    
    @Test
    public void controller_test_all() throws Exception {
        
        when(service.getAllOrgss()).thenReturn(Arrays.asList(new Organizations(1,"abc"),new Organizations(2,"xyz")));
        
        RequestBuilder request = MockMvcRequestBuilders
                 .get("/orgs")
                 .accept(MediaType.APPLICATION_JSON);
        
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"name\":\"abc\"},{\"id\":2,\"name\":\"xyz\"}]"))
                .andReturn();
    }
    
    @Test
    public void controller_test_one() throws Exception {
        
        Organizations value = new Organizations(1,"abc");  
        when(service.getOrg(1)).thenReturn(value);
        RequestBuilder request = MockMvcRequestBuilders
                 .get("/orgs/1")
                 .accept(MediaType.APPLICATION_JSON);
        
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"abc\"}"))
                .andReturn();



    }
    
}