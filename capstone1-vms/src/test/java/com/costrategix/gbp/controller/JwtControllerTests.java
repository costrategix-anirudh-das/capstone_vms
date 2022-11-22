//
//
//package com.costrategix.gbp.controller;
//
//
//import com.costrategix.gbp.Application;
//import com.costrategix.gbp.ApplicationTests;
//import com.costrategix.gbp.config.JwtUtil;
//import com.costrategix.gbp.entity.AuthRequest;
//import com.costrategix.gbp.entity.Organizations;
//import com.costrategix.gbp.entity.User;
//import com.costrategix.gbp.filter.JwtFilter;
//import com.costrategix.gbp.service.CustomUserDetailsService;
//import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.ObjectWriter;
//
//import lombok.val;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.SpringSecurityCoreVersion;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.RequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//public class JwtControllerTests extends ApplicationTests {
//	
//	ObjectMapper objectMapper = new ObjectMapper();
//	ObjectWriter objectWriter = objectMapper.writer();
//
//	private MockMvc mockMvc;
//	
//	@Autowired
//    private WebApplicationContext web;
//	
//	@MockBean
//	private JwtUtil jwtUtil;
//	
//	@MockBean
//	private User user;
//	
//	
//	@MockBean
//	private JwtFilter jwtFilter;
//
//	@MockBean
//	AuthRequest authRequest;
//	
//	@MockBean
//	CustomUserDetailsService customUserDetailsService;
//	
//
//	
//	@MockBean
//	AuthenticationManager authenticationManager;
//	
//	
//	 private static org.springframework.security.core.userdetails.User dummy;
//	 private static String jwtToken;
//	
//	@Before
//	public void setUp() {
//		
//		mockMvc = MockMvcBuilders.webAppContextSetup(web).build();
//		Map<String, Object> claims = new HashMap<>();
//	    dummy = new org.springframework.security.core.userdetails.User("anirudh@gmail.com", "password", new ArrayList<>());
//	    jwtToken = jwtUtil.createToken(claims, "anirudh@gmail.com");
//	    System.out.println(jwtToken);
//	}
////	"{\"emailAdress\":\"" + "anirudh@gmail.com" + "\", \"password\":\"" + "password" + "\"}"
//	@Test
//    public void testLoginReturnsJwt() throws Exception {
//		
//
//        authRequest.setEmailAdress("anirudh@gmail.com");
//        authRequest.setPassword("password");
//        objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
//        String req = objectWriter.writeValueAsString(authRequest);
//        
//        RequestBuilder request = MockMvcRequestBuilders
//                .post("/authenticate")
//                .content(req)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .accept(MediaType.APPLICATION_JSON);
//        when(customUserDetailsService.loadUserByUsername("anirudh@gmail.com")).thenReturn(dummy);
//        when(jwtUtil.generateToken("anirudh@gmail.com")).thenReturn(jwtToken);
//        System.out.println(authRequest.getEmailAdress());
//        when(jwtUtil.validateToken(jwtToken, dummy)).thenReturn(true);
//        System.out.println(request);
//        MvcResult mvcResult = mockMvc.perform(request)
//                .andExpect(status().isOk())
//                .andReturn();
//    }
//	
//	
//	@Test
//    public void testLoginReturnsJwt2() throws Exception {
//		
//
//        authRequest.setEmailAdress("anirudh@gmail.com");
//        authRequest.setPassword("password");
//        objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
//        String req = objectWriter.writeValueAsString(authRequest);
//        
//        RequestBuilder request = MockMvcRequestBuilders
//                .post("/authenticate")
//                .header("Authorization", jwtToken)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .accept(MediaType.APPLICATION_JSON);
//        when(customUserDetailsService.loadUserByUsername("anirudh@gmail.com")).thenReturn(dummy);
//        when(jwtUtil.generateToken("anirudh@gmail.com")).thenReturn(jwtToken);
//        System.out.println(authRequest.getEmailAdress());
//        when(jwtUtil.validateToken(jwtToken, dummy)).thenReturn(true);
//        System.out.println(request);
//        MvcResult mvcResult = mockMvc.perform(request)
//                .andExpect(status().isOk())
//                .andReturn();
//    }
//
//}
//	
//
