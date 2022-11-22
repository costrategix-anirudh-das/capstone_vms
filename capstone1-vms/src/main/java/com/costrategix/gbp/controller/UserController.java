package com.costrategix.gbp.controller;


import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;


import javax.mail.MessagingException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.costrategix.gbp.dto.UserResponseDto;
import com.costrategix.gbp.entity.User;


import com.costrategix.gbp.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;

	@PostMapping("/user")
	public User send_users(@RequestBody User user) throws UnsupportedEncodingException, MessagingException {
		return userService.add_user(user);
	}
	
	@GetMapping("/RegisterLink")
	public User Validatetoken(@RequestParam String token,@RequestParam String email) throws UnsupportedEncodingException, MessagingException {
		return userService.validate_token(token,email);
	}	
	
	@PutMapping("/user/update")
	public User send_users_update(@RequestBody User user) {
		return userService.send_users_update(user);
	}
	@GetMapping("/user/{id}")
	public UserResponseDto getItem(@PathVariable int id) {
		 return userService.getItem(id);
	}
	
	@GetMapping("/user/org/{org_id}")
	public List<UserResponseDto> getusers(@PathVariable int org_id) {
		return userService.getusers(org_id);
	 }
	
}
