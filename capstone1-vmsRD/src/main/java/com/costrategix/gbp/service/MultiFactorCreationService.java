package com.costrategix.gbp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.costrategix.gbp.entity.MultiFactorRequest;
import com.costrategix.gbp.repository.MultiFactorRequestRepo;
import net.bytebuddy.utility.RandomString;

@Service
public class MultiFactorCreationService {

	@Autowired
	private MultiFactorRequestRepo multiFactorRequestRepo;
	
	
	public String multiFactorCreation(int userId) {
		
		String OTP = RandomString.make(6);
		MultiFactorRequest mul = new MultiFactorRequest(userId, OTP);
		multiFactorRequestRepo.save(mul);
		return OTP;
	}
}
