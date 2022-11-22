package com.costrategix.gbp.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.costrategix.gbp.entity.MultiFactorRequest;
import com.costrategix.gbp.repository.MultiFactorRequestRepo;

@Service
public class ValidateMultiFactorService {
	
	@Autowired
	MultiFactorRequestRepo multiFactorRequestRepo;
	
	public String validateMultiFactor(int userId, String mfaValue) {
		
		MultiFactorRequest mfa = multiFactorRequestRepo.findByUserId(userId).get();
		if (!(mfa.getCodevalue().equals(mfaValue))) {
			return "Wrong OTP";
		}
		
		else if(mfa.getExpireddate().compareTo(LocalDateTime.now()) < 0) {
			return "Otp expired";
		}
		return "Valid Otp";
	}
}
