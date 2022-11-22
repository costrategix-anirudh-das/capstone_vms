package com.costrategix.gbp.controller;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.aspectj.apache.bcel.classfile.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.costrategix.gbp.common.Email;
import com.costrategix.gbp.common.PasswordEncryption;
import com.costrategix.gbp.entity.Invite;
import com.costrategix.gbp.entity.MultiFactorRequest;
import com.costrategix.gbp.entity.NewPassword;
import com.costrategix.gbp.entity.PasswordResetRequest;
import com.costrategix.gbp.entity.User;
import com.costrategix.gbp.repository.InviteRepo;
import com.costrategix.gbp.repository.MultiFactorRequestRepo;
import com.costrategix.gbp.repository.PasswordResetRepo;
import com.costrategix.gbp.repository.UserRepo;
import com.costrategix.gbp.service.ForgetPasswordService;
import com.costrategix.gbp.service.MultiFactorCreationService;
import com.costrategix.gbp.service.ValidateMultiFactorService;

import net.bytebuddy.utility.RandomString;

@RestController
public class ForgetPasswordController {
	
	@Autowired 
	ForgetPasswordService passwordService;

	@GetMapping("/resetmail/{userId}")
	public User receive_email(@PathVariable int userId) throws UnsupportedEncodingException, MessagingException {
		return passwordService.receive_email(userId);
	}
	
	@Transactional
	@GetMapping("/validate_token")
	public User Validatetoken(@RequestParam String token) {
		return passwordService.Validatetoken(token);				
	}
	
	@Transactional
	@PutMapping("/update_password/{userid}")
	public String update(@PathVariable int userid, @RequestBody String newPassword) {
		return passwordService.check(userid, newPassword);
	}
	
	
	@Transactional
	@PutMapping("/update_password/user/{userid}")
	public String updatePassword(@PathVariable int userid, @RequestBody NewPassword newPassword) {
		System.out.println(newPassword.toString());
		System.out.println(newPassword.getClass());
		return passwordService.updatePassword(userid, newPassword);
	}
	
	@Transactional
	@PutMapping("/create_password/user/{userid}")
	public String createPassword(@PathVariable int userid, @RequestBody NewPassword newPassword) throws UnsupportedEncodingException, MessagingException {
		
		return passwordService.createPassword(userid, newPassword);
	
	}
	
	@GetMapping("/mfavalidate/user/{userId}")
	public String checkMfaValidate(@PathVariable int userId,@RequestParam String mfaValue) throws UnsupportedEncodingException, MessagingException{
		return passwordService.checkMfaValidate(userId, mfaValue);
	}
	

}
