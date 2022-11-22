package com.costrategix.gbp.service;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.costrategix.gbp.common.Email;
import com.costrategix.gbp.common.PasswordEncryption;
import com.costrategix.gbp.entity.MultiFactorRequest;
import com.costrategix.gbp.entity.NewPassword;
import com.costrategix.gbp.entity.PasswordResetRequest;
import com.costrategix.gbp.entity.User;
import com.costrategix.gbp.entity.UserAccountStatus;
import com.costrategix.gbp.repository.InviteRepo;
import com.costrategix.gbp.repository.MultiFactorRequestRepo;
import com.costrategix.gbp.repository.PasswordResetRepo;
import com.costrategix.gbp.repository.UserAccountRepo;
import com.costrategix.gbp.repository.UserRepo;

import net.bytebuddy.utility.RandomString;

@Service
public class ForgetPasswordService {
	@Autowired
	UserRepo userRepo;
	  
	@Autowired
	Email send_email;

	@Autowired
	UserAccountRepo accountrepo;
	
	@Autowired
	PasswordResetRepo resetRepo;
	
	@Autowired
	InviteRepo inviterepo;
	
	@Autowired
	ValidateMultiFactorService validateMultiFactorService;
	
	@Autowired
	MultiFactorRequestRepo multiFactorRequestRepo;
	
	@Autowired
	private MultiFactorCreationService creation;
	

	
	public User password(int id, NewPassword newPassword) {
		System.out.println("hi");
		User user = userRepo.findById(id).get();
		String pwd = PasswordEncryption.encrypt(newPassword.getNewPassword());
		user.setSecurityHash(pwd);
		userRepo.save(user);
		return user;
	}
	
	
	public User receive_email(@PathVariable int userId) throws UnsupportedEncodingException, MessagingException{
		Optional<User> users = userRepo.findById(userId);
		String email = users.get().getEmailAdress();
		if(!users.isEmpty()) {
			System.out.println("exists");
			int i = users.get().getId();
			String token = RandomString.make(30);
			resetRepo.save(new PasswordResetRequest(i,token));
		    String resetPasswordLink ="reset_password?token=" + token;
		    send_email.sendEmail(email, resetPasswordLink,2,users.get().getFirstName(),users.get().getLastName());
			
		}
		return userRepo.findByEmailAdress(email).get();
	}
	
	
	@Transactional
	public User Validatetoken(String token) {
		User user=null;
		PasswordResetRequest tokens = resetRepo.findByResetToken(token).get();
			LocalDateTime expiry = tokens.getExpiryDate();
			int id = tokens.getUserId();
			System.out.println("hello");
			user =  userRepo.findById(id).get();
			
			if(expiry.compareTo(LocalDateTime.now())>0)
			{
				resetRepo.removeByUserId(id);	
 				return user;
				
			}
			else
			{
				return null;
			}
	}
	
	@Transactional
	public String updatePassword(int id,NewPassword newPassword) {
		
		//PasswordResetRequest tokens = resetRepo.findByResetToken(token).get();
		//int id = tokens.getUserId();
		User user = password(id,newPassword);
			
 		return "Successfully addded new password";
	}
	
	@Transactional
	public String createPassword(int userid, NewPassword newPassword) throws UnsupportedEncodingException, MessagingException {
		
		User user = password(userid,newPassword);
		String phone_number = newPassword.getPhonenumber();
		System.out.println("ytre");
		if(phone_number.matches("^(\\+91[\\-\\s]?)?[0]?(91)?[789]\\d{9}$"))
		{
			System.out.println("he");
			user.setPhoneNumber(phone_number);
			userRepo.save(user);
		}
		else
		{
			return "please recheck mobile number";
		}
		String value = creation.multiFactorCreation(userid);
		send_email.sendmfaEmail(user,value,"Your mfa code",3);
 		return "Successfully created new password";
	
	}
	public String check(int id, String pass) {
		return "hello";
	}
	
	public String checkMfaValidate(int userId, String mfaValue) throws UnsupportedEncodingException, MessagingException{
		
		String mfaVal = validateMultiFactorService.validateMultiFactor(userId, mfaValue);
		User user = userRepo.findById(userId).get();
		UserAccountStatus status = new UserAccountStatus(2,"active");
		accountrepo.save(status);
		user.setUserAccountStatus(status);
		if (mfaVal == "Wrong OTP") {
			return mfaVal;
		}
		else if (mfaVal == "Otp expired") {
			
			MultiFactorRequest mfa = multiFactorRequestRepo.findByUserId(userId).get();
			String OTP = RandomString.make(6);
			mfa.setCodevalue(OTP);
			mfa.setCreatedtime(LocalDateTime.now());
			mfa.setExpireddate(LocalDateTime.now().plusMinutes(30));
			multiFactorRequestRepo.save(mfa);
			send_email.sendmfaEmail(user,OTP,"Your mfa code",3);
			return mfaVal;
		}
		return mfaVal;
	}

	@Transactional
	public User Login(String email) {
		
		User user = userRepo.findByEmailAdress(email).get();
    	MultiFactorRequest multiFactorRequest = multiFactorRequestRepo.findByUserId(user.getId()).get();
    	LocalDateTime start = multiFactorRequest.getCreatedtime();
    	System.out.println("Checking github or gitlab");
    	start = start.plusDays(30);
    	if (start.compareTo(LocalDateTime.now())>=0) {
    		return user;
    	}
    	else {
    		return null;
    	}
	}
	
	
}
