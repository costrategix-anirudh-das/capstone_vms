//package com.costrategix.gbp.controller;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.skyscreamer.jsonassert.JSONAssert;
//import org.skyscreamer.jsonassert.JSONCompareMode;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.RequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import com.costrategix.gbp.ApplicationTests;
//import com.costrategix.gbp.common.OrganizationLocations;
//import com.costrategix.gbp.entity.Organizations;
//import com.costrategix.gbp.entity.Roles;
//import com.costrategix.gbp.entity.location_group;
//import com.costrategix.gbp.entity.subLocations;
//import com.costrategix.gbp.service.LocationService;
//
//
//
//
//
//public class LocationTests extends  ApplicationTests {
//	
//	
//	@Autowired
//	private WebApplicationContext web;
//	
//	private MockMvc mockMvc;
//	
//	@MockBean
//	private LocationService service;
//	
//
//
//
//	@Before 
//	public void setup()
//	{
//		mockMvc = MockMvcBuilders.webAppContextSetup(web).build();
//	}
//	
//	@Test
//	public void grouplocation() throws Exception {
//
//		Set<Organizations> org = new HashSet<>();
//		org.add(new Organizations(1,"abc"));
//		subLocations sublocation = new subLocations(1,"banglore",new location_group(1,"karnataka"),org);
//		Set<location_group> group = new HashSet<>();
//		group.add(sublocation.getLg());
//		when(service.getlocationgroups(1)).thenReturn(group);
//
//		RequestBuilder request = MockMvcRequestBuilders.get("/organization/{orgId}/locations",1).accept(MediaType.APPLICATION_JSON);
//
//		MvcResult result = mockMvc.perform(request).andExpect(status().isOk())
//				.andExpect(content().json("[{\"loc_id\":1,\"statename\":\"karnataka\"}]")).andReturn();
//	}
//
//
//	@Test
//	public void subloactions() throws Exception {
//		
//		Set<Organizations> org = new HashSet<>();
//		org.add(new Organizations(1,"abc"));
//		List<subLocations> sublocation = new ArrayList<>(); 
//			sublocation.add(new subLocations(1,"banglore",new location_group(1,"karnataka"),org));
//		
//		when(service.getsublocation(1, 1)).thenReturn(sublocation);
//		
//		
//		RequestBuilder request = MockMvcRequestBuilders.get("/organization/{orgId}/location/{grpLocId}",1,1).accept(MediaType.APPLICATION_JSON);
//
//		MvcResult result = mockMvc.perform(request).andExpect(status().isOk())
//				.andExpect(content().json("[{\"sub_loc_id\":1,\"sub_loc_name\":\"banglore\"}]")).andReturn();
//
//	}
//	
//	
//	@Test
//	public void getAllLocations() throws Exception {
//		List<OrganizationLocations> L = new ArrayList<>(); 
//		location_group lg_1 = new location_group(1,"Karnataka");
//		location_group lg_2 = new location_group(2,"Maharastra");
//		
//		subLocations sub_1 = new subLocations(1,"Mysore",lg_1,null);
//		subLocations sub_2 = new subLocations(2,"Bangalore",lg_1,null);
//		subLocations sub_3 = new subLocations(3,"Mumbai",lg_2,null);
//		
//		List<subLocations> Kl = new ArrayList<>();Kl.add(sub_1);Kl.add(sub_2);
//		List<subLocations> Ml = new ArrayList<>();Ml.add(sub_3);
//		
//		OrganizationLocations ol_1 = new OrganizationLocations(1,lg_1.getStatename(),Kl);
//		OrganizationLocations ol_2 = new OrganizationLocations(1,lg_2.getStatename(),Ml);
//		L.add(ol_2);L.add(ol_1);
//		when(service.getAll(any(Integer.class))).thenReturn(L);
//		
//		RequestBuilder request = MockMvcRequestBuilders
//				.get("/location/{orgId}",1)
//				.accept(MediaType.APPLICATION_JSON);
//		MvcResult result = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
//		System.out.println(result.getResponse().getContentAsString());
//		JSONAssert.assertEquals("[{\"id\":1,\"locationName\":\"Maharastra\",\"sublocations\":"
//				+ "[{\"sub_loc_id\":3,\"sub_loc_name\":\"Mumbai\"}]},{\"id\":1,\"locationName\":\"Karnataka\","
//				+ "\"sublocations\":[{\"sub_loc_id\":1,\"sub_loc_name\":\"Mysore\"},"
//				+ "{\"sub_loc_id\":2,\"sub_loc_name\":\"Bangalore\"}]}]",
//				  result.getResponse().getContentAsString() ,
//				  JSONCompareMode.STRICT);
//		
//		
//	}
//
//}
