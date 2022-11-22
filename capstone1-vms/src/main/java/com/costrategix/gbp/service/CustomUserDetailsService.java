package com.costrategix.gbp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.costrategix.gbp.common.PasswordEncryption;
import com.costrategix.gbp.entity.AuthRequest;
import com.costrategix.gbp.entity.User;
import com.costrategix.gbp.repository.UserRepo;


@Service(value="UserDetailsService")
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepo repository;
	
	@Autowired
	PasswordEncryption passwordEncryption;
	
	@Autowired
	private ForgetPasswordService forgetPasswordService;
	
	private static final Logger LOGGER = LogManager.getLogger(CustomUserDetailsService.class);
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		LOGGER.info("Retrieving user based on email : ", email);
		Optional<User> users = repository.findByEmailAdress(email);

		
		return new org.springframework.security.core.userdetails.User(users.get().getEmailAdress(), users.get().getSecurityHash(), new ArrayList<>());

	}
	
	

}
