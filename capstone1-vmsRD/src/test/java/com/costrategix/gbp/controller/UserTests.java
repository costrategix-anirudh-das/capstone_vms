//package com.costrategix.gbp.controller;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.io.UnsupportedEncodingException;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//
//import javax.mail.MessagingException;
//import javax.net.ssl.SSLEngineResult.Status;
//
//import org.assertj.core.internal.bytebuddy.utility.RandomString;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.skyscreamer.jsonassert.JSONAssert;
//import org.skyscreamer.jsonassert.JSONCompareMode;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.RequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import com.costrategix.gbp.ApplicationTests;
//import com.costrategix.gbp.dto.UserResponseDto;
//import com.costrategix.gbp.entity.Invite;
//import com.costrategix.gbp.entity.Organizations;
//import com.costrategix.gbp.entity.Roles;
//import com.costrategix.gbp.entity.User;
//import com.costrategix.gbp.entity.UserAccountStatus;
//import com.costrategix.gbp.entity.location_group;
//import com.costrategix.gbp.entity.subLocations;
//import com.costrategix.gbp.repository.UserRepo;
//import com.costrategix.gbp.service.UserService;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.ObjectWriter;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//
//
//	
//public class UserTests extends  ApplicationTests {
//	
//	@Autowired
//	private WebApplicationContext web;
//	
//	@MockBean
//	UserService service;
//	
//	@Autowired
//	UserRepo repo;
//	
//	private MockMvc mockMvc;
//	
//	ObjectMapper objectMapper = new ObjectMapper();
//	ObjectWriter objectWriter = objectMapper.writer();
//	
//	@Before 
//	public void setup()
//	{
//		mockMvc = MockMvcBuilders.webAppContextSetup(web).build();
//	}
//	
//	@Test
//	public void update_user() throws Exception {
//		
//		location_group lg = new location_group(1,"state1");
//		Set<location_group> Y = new HashSet<>();Y.add(lg);
//		User u = new User(1,"Naveen","MS","xyz@gmail.com",900,null,"admin","9940336216",Y,null);
//		u.setLdt(null);
//		u.setLastLoginDateTime(null);
//		u.setUpdated(null);
//		u.setUserStatusChangeDateTime(null);
//		u.setUserAccountStatus(new UserAccountStatus(1,"Active"));
//
//		User user_update = new User(1,"Naveen","MS","abc@gmail.com",900,null,"admin","9940336216",Y,null);
//		user_update.setLdt(null);
//		user_update.setLastLoginDateTime(null);
//		user_update.setUpdated(null);
//		user_update.setUserStatusChangeDateTime(null);
//		Set<Roles> L = new HashSet<>();L.add(new Roles(1,"role1"));
//		user_update.setRoless(L);
//		user_update.setUserAccountStatus(new UserAccountStatus(2,"Inactive"));
//		
//		String old = objectWriter.writeValueAsString(u);
//		String neww = objectWriter.writeValueAsString(user_update);
//		
//		when(service.send_users_update(any(User.class))).thenReturn(user_update);
//		
//		RequestBuilder request = MockMvcRequestBuilders.put("/user/update")
//									.accept(MediaType.APPLICATION_JSON)
//									.content(old)
//									.contentType(MediaType.APPLICATION_JSON);
//		MvcResult result = mockMvc.perform(request).andExpect(status().isOk())
//								.andReturn();
//		System.out.println(result.getResponse().getContentAsString());
//		JSONAssert.assertEquals("{\"id\":1,\"emailAdress\":\"abc@gmail.com\",\"orgid\":900,\"statusId\":0,\"userAccountStatus\":{\"id\":2,\"description\":\"Inactive\"},"
//+ "\"roless\":[{\"id\":1,\"role_name\":\"role1\"}],\"groupLocationId\":null,\"userLocationgroup\":[{\"loc_id\":1,\"statename\":\"state1\"}],\"firstName\":\"Naveen\","
//+ "\"phoneNumber\":\"9940336216\",\"lastName\":\"MS\",\"jobTitle\":\"admin\"}\r\n",
//				  result.getResponse().getContentAsString() ,
//				  JSONCompareMode.LENIENT);
//		
//	}
//	
//	
//	@Test
//	public void validate_token() throws Exception {
//		String token = RandomString.make(30);
//		
//		location_group lg = new location_group(1,"state1");
//		Set<location_group> Y = new HashSet<>();Y.add(lg);
//		User u = new User(1,"Naveen","MS","xyz@gmail.com",900,null,"admin","9940336216",Y,null);
//		u.setLdt(null);
//		u.setLastLoginDateTime(null);
//		u.setUpdated(null);
//		u.setUserStatusChangeDateTime(null);
//		u.setUserAccountStatus(new UserAccountStatus(1,"Active"));
//		
//		Invite in = new Invite("xyz@gmail.com",token,900,1);
//		String user = objectWriter.writeValueAsString(u);
//		if(in.getExpireDateTime().compareTo(LocalDateTime.now())>0) {
//			when(service.validate_token(token,"xyz@gmail.com")).thenReturn(u);
//		}
//		else {
//			when(service.validate_token(token,"xyz@gmail.com")).thenReturn(null);
//		}
//		
//		RequestBuilder request = MockMvcRequestBuilders.get("/RegisterLink").param("token", token).param("email", "xyz@gmail.com").accept(MediaType.APPLICATION_JSON);
//		MvcResult mvc_result = mockMvc.perform(request)
//		.andExpect(status().isOk())
//		.andExpect(content().json(user))
//		.andReturn();
//	}
//
//	@Test
//	public void post_user() throws Exception {
//		location_group lg = new location_group(1,"state1");
//		Set<location_group> Y = new HashSet<>();Y.add(lg);
//		User user = new User(1,"Naveen","MS","xyz@gmail.com",900,null,"admin","9940336216",Y,null);
//		user.setLdt(null);
//		user.setLastLoginDateTime(null);
//		user.setUpdated(null);
//		user.setUserStatusChangeDateTime(null);
//		user.setUserAccountStatus(new UserAccountStatus(1,"Active"));
//		
//		when(service.add_user(any(User.class))).thenReturn(user);
//		
//		String content = objectWriter.writeValueAsString(user);
//		//System.out.println(content);	 
//		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user")
//										   .accept(MediaType.APPLICATION_JSON)
//				 						   .content(content)
//				 						   .contentType(MediaType.APPLICATION_JSON);
//		 MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn(); 
//		 //System.out.println("Here "+result.getResponse().getContentAsString());
//		 JSONAssert.assertEquals(
//				  "{\"id\":1,\"emailAdress\":\"xyz@gmail.com\",\"orgid\":900,\"jobTitle\":\"admin\"}",
//				  result.getResponse().getContentAsString() ,
//				  JSONCompareMode.LENIENT);
//	}
//	
//	@Test
//	public void get_user_by_id() throws Exception {
//		location_group lg = new location_group(1,"state1");
//		Set<location_group> LG = new HashSet<>();LG.add(lg);
//		User u = new User(1,"Naveen","MS","xyz@gmail.com",900,null,"admin","9940336216",LG,null);	
//	
//
//		Organizations orgs = new Organizations(900,"Apollo");
//		location_group locs = new location_group(1,"Karnataka");
//		subLocations sub_locs = new subLocations();
//		sub_locs.setSub_loc_name("Bangalore");
//		
//        UserResponseDto userResponse = new UserResponseDto(u.getId(), u.getFirstName(), u.getLastName(),
//      		  u.getEmailAdress(), u.getJobTitle(), "2022-11-15T11:56:59.501888800" ,
//      		  orgs, u.getUserAccountStatus() , u.getRoless(),
//      		  u.getPhoneNumber(), u.getUserLocationgroup(), u.getUsersubLocationgroup());		
//        
//        
//		when(service.getItem(1)).thenReturn(userResponse);
//		
//		RequestBuilder request = MockMvcRequestBuilders.get("/user/{id}",1).accept(MediaType.APPLICATION_JSON);
//	       
//		
//
//		
//		MvcResult mvc_result = mockMvc.perform(request)
//				.andExpect(status().isOk())
//				.andExpect(content().json("{\"id\":1,\"emailAdress\":\"xyz@gmail.com\",\"created_at\":\"2022-11-15T11:56:59.501888800\",\"org\":{\"id\":900,\"name\":\"Apollo\"},\"status\":null,\"roles\":[],\"userLocationgroup\":[{\"loc_id\":1,\"statename\":\"state1\"}],\"usersubLocationgroup\":null,\"firstName\":\"Naveen\",\"lastName\":\"MS\",\"jobTitle\":\"admin\",\"phoneNumber\":\"9940336216\"}"))
//				.andReturn();
//		System.out.println(mvc_result.getResponse().getContentAsString());
//		
//	}
//	
//	
//	
//	@Test
//	public void get_user() throws Exception {
//		location_group lg = new location_group(1,"state1");
//		Set<location_group> LG = new HashSet<>();LG.add(lg);
//		User u = new User(1,"Naveen","MS","xyz@gmail.com",900,null,"admin","9940336216",LG,null);	
//		
//		
//		Roles r = new Roles(1,"role1");
//		List<Roles> Y = new ArrayList<>();Y.add(r);
//		Organizations orgs = new Organizations(900,"Apollo");
//		location_group locs = new location_group(1,"Karnataka");
//		subLocations sub_locs = new subLocations();
//		sub_locs.setSub_loc_name("Bangalore");
//		
//        UserResponseDto userResponse = new UserResponseDto(u.getId(), u.getFirstName(), u.getLastName(),
//        		  u.getEmailAdress(), u.getJobTitle(), "2022-11-15T11:56:59.501888800" ,
//        		  orgs, u.getUserAccountStatus() , u.getRoless(),
//        		  u.getPhoneNumber(), u.getUserLocationgroup(), u.getUsersubLocationgroup());	
//		
//		
//        List<UserResponseDto> response = new ArrayList<>();response.add(userResponse);
//		when(service.getusers(900)).thenReturn(response);
//		
//		RequestBuilder request = MockMvcRequestBuilders.get("/user/org/{org_id}",900).accept(MediaType.APPLICATION_JSON);
//		MvcResult mvc_result = mockMvc.perform(request)
//				.andExpect(status().isOk())
//				.andExpect(content().json("[{\"id\":1,\"emailAdress\":\"xyz@gmail.com\",\"created_at\":\"2022-11-15T11:56:59.501888800\","
//						+ "\"org\":{\"id\":900,\"name\":\"Apollo\"},\"status\":null,\"roles\":[],\"userLocationgroup\":[{\"loc_id\":1,\"statename\":\"state1\"}],\"usersubLocationgroup\":null,"
//						+ "\"phoneNumber\":\"9940336216\",\"jobTitle\":\"admin\",\"firstName\":\"Naveen\",\"lastName\":\"MS\"}]"))
//				.andReturn();
//		System.out.println(mvc_result.getResponse().getContentAsString());
//	}
//
//}
