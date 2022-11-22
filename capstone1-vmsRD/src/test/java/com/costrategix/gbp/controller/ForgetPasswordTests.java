package com.costrategix.gbp.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.costrategix.gbp.ApplicationTests;
import com.costrategix.gbp.common.Email;
import com.costrategix.gbp.common.PasswordEncryption;
import com.costrategix.gbp.entity.MultiFactorRequest;
import com.costrategix.gbp.entity.NewPassword;
import com.costrategix.gbp.entity.PasswordResetRequest;
import com.costrategix.gbp.entity.Roles;
import com.costrategix.gbp.entity.User;
import com.costrategix.gbp.repository.MultiFactorRequestRepo;
import com.costrategix.gbp.repository.UserRepo;
import com.costrategix.gbp.service.ForgetPasswordService;
import com.costrategix.gbp.service.MultiFactorCreationService;
import com.costrategix.gbp.service.ValidateMultiFactorService;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.bytebuddy.utility.RandomString;
import springfox.documentation.spring.web.json.Json;


public class ForgetPasswordTests  extends  ApplicationTests {

	
	@Autowired
	private WebApplicationContext web;
	
	private MockMvc mockMvc;
	
	@MockBean
	private ForgetPasswordService service;
	
	@Autowired 
	ObjectMapper mapper;
	
	@MockBean
	private UserRepo  repo;
	
	@MockBean
	private MultiFactorCreationService creation;
	
	@MockBean
	ValidateMultiFactorService validate;
	
	@MockBean
	MultiFactorRequestRepo multiFactorRequestRepo;
	
	@MockBean
	Email send_email;

	
	@Before 
	public void setup()
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(web).build();
	}
	
	@Test
	public void validateresettoken() throws Exception {
		List<Integer> role = Arrays.asList(1);
		Roles r = new Roles(1,"role1");
		User user = new User(1,"aish","d","aishwarya@gmail.com",1,role,null,null,null,null);
		String t = RandomString.make(30);
		PasswordResetRequest tokens = new PasswordResetRequest(user.getId(),t);
		
		when(service.Validatetoken(t)).thenReturn(user);
			
		RequestBuilder request = MockMvcRequestBuilders.get("/validate_token").param("token", t).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(request).andExpect(status().isOk())
				.andExpect(content().json("{\"id\":1,\"emailAdress\":\"aishwarya@gmail.com\",\"orgid\":1,\"role_id\":[1],\"firstName\":\"aish\",\"lastName\":\"d\",\"phoneNumber\":null,\"jobTitle\":null}")).andReturn();
	}
	
	@Test
	public void validateresettokenfailed() throws Exception {
		List<Integer> role = Arrays.asList(1);
		Roles r = new Roles(1,"role1");
		User user = new User(1,"aish","d","aishwarya@gmail.com",1,role,null,null,null,null);
		String t = RandomString.make(30);
		PasswordResetRequest tokens = new PasswordResetRequest(user.getId(),t);
		if(tokens.getExpiryDate().compareTo(LocalDateTime.now().plusDays(2))<0)
		
			{
			System.out.println("hello");
			when(service.Validatetoken(t)).thenReturn(null);
			}
			
		RequestBuilder request = MockMvcRequestBuilders.get("/validate_token").param("token", t).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(request).andExpect(status().isOk())
				.andReturn();
	}
	
	@Test
	public void updatepassword() throws Exception {
		List<Integer> role = Arrays.asList(1);
		Roles r = new Roles(1,"role1");
		User user = new User(2,"aish","d","aishwaryad@gmail.com",1,role,null,null,null,null);
		NewPassword pass = new NewPassword("tredings","tredings");
		String pwd = PasswordEncryption.encrypt(pass.getNewPassword());
		user.setSecurityHash(pwd);
		when(repo.save(user)).thenReturn(user);
		when(service.updatePassword(any(Integer.class), any(NewPassword.class))).thenReturn("Successfully updated new password");
		System.out.println(pass);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/update_password/user/{userid}",2).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(pass));
			System.out.println(request);
		
		mockMvc.perform(request).andExpect(status().isOk()).andExpect(content().string("Successfully updated new password"))
				.andReturn();
		
		assertEquals(user.getSecurityHash(),pwd);
		
	}
	
	@Test
	public void createpassword() throws Exception {
		List<Integer> role = Arrays.asList(1);
		Roles r = new Roles(1,"role1");
		User user = new User(2,"aish","d","aishwaryad@gmail.com",1,role,null,null,null,null);
		NewPassword pass = new NewPassword("tredings","tredings","8765343457");
		String pwd = PasswordEncryption.encrypt(pass.getNewPassword());
		user.setSecurityHash(pwd);
		user.setPhoneNumber(pass.getPhonenumber());
		when(repo.save(user)).thenReturn(user);
		String OTP = RandomString.make(6);
		MultiFactorRequest mul = new MultiFactorRequest(user.getId(), OTP);
		when(creation.multiFactorCreation(user.getId())).thenReturn(OTP);
		when(service.createPassword(any(Integer.class), any(NewPassword.class))).thenReturn("Successfully created new password");
		System.out.println(pass);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/create_password/user/{userid}",2).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(pass));
			System.out.println(request);
		
		mockMvc.perform(request).andExpect(status().isOk()).andExpect(content().string("Successfully created new password"))
				.andReturn();
		
		assertEquals(user.getSecurityHash(),pwd);
		assertEquals(LocalDateTime.now().getHour(),mul.getCreatedtime().getHour());
		assertEquals(user.getPhoneNumber(),pass.getPhonenumber());
		
	}
	
	@Test
	public void mfavalidate() throws Exception{
		
		List<Integer> role = Arrays.asList(1);
		Roles r = new Roles(1,"role1");
		String OTP = RandomString.make(6);
		User user = new User(1,"aish","d","aishwaryad@gmail.com",1,role,null,null,null,null);
		MultiFactorRequest multi = new MultiFactorRequest(user.getId(),OTP);
		when(validate.validateMultiFactor(user.getId(), OTP)).thenReturn("Valid OTP");
		when(service.checkMfaValidate(user.getId(), OTP)).thenReturn("Valid Otp");
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/mfavalidate/user/{userId}",1).param("mfaValue", OTP).accept(MediaType.APPLICATION_JSON);
		System.out.println(request);
	
	mockMvc.perform(request).andExpect(status().isOk()).andExpect(content().string("Valid Otp"))
			.andReturn();
	}
	
	
	@Test
	public void mfavalidateexpired() throws Exception{
		
		List<Integer> role = Arrays.asList(1);
		Roles r = new Roles(1,"role1");
		String OTP = RandomString.make(6);
		User user = new User(1,"aish","d","aishwaryad@gmail.com",1,role,null,null,null,null);
		MultiFactorRequest multi = new MultiFactorRequest(user.getId(),OTP);
		when(validate.validateMultiFactor(user.getId(), OTP)).thenReturn("Otp expired");
		System.out.println(OTP);
		String newotp = RandomString.make(6);
		multi.setCodevalue(OTP);
		multi.setCreatedtime(LocalDateTime.now());
		multi.setExpireddate(LocalDateTime.now().plusMinutes(30));
		when(multiFactorRequestRepo.save(multi)).thenReturn(multi);
		when(service.checkMfaValidate(user.getId(), OTP)).thenReturn("Otp expired");
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/mfavalidate/user/{userId}",1).param("mfaValue", OTP).accept(MediaType.APPLICATION_JSON);
			System.out.println(request);
	
	mockMvc.perform(request).andExpect(status().isOk()).andExpect(content().string("Otp expired"))
			.andReturn();
	}
	
	@Test
	public void recieve_mail() throws Exception{
		
		List<Integer> role = Arrays.asList(1);
		Roles r = new Roles(1,"role1");
		User user = new User(1,"aish","d","aishwaryad@gmail.com",1,role,null,null,null,null);
		//String token = RandomString.make(30);
		//PasswordResetRequest pass = new PasswordResetRequest(user.getId(),token);
	    //String resetPasswordLink ="reset_password?token=" + token;
	    //Mockito.doThrow(new Exception()).doNothing().when(send_email).sendEmail(user.getEmailAdress(),resetPasswordLink,2,user.getFirstName(),user.getLastName());
		when(service.receive_email(user.getId())).thenReturn(user);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/resetmail/{userId}",user.getId()).accept(MediaType.APPLICATION_JSON);
		System.out.println(request);
	
		mockMvc.perform(request).andExpect(status().isOk()).andExpect(content().json(("{\"id\":1,\"emailAdress\":\"aishwaryad@gmail.com\",\"orgid\":1,\"role_id\":[1],\"firstName\":\"aish\",\"lastName\":\"d\",\"phoneNumber\":null,\"jobTitle\":null}"))).andReturn();
	
	}
	
}
