package com.costrategix.gbp.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.costrategix.gbp.common.Email;
import com.costrategix.gbp.config.JwtUtil;
import com.costrategix.gbp.entity.AuthRequest;
import com.costrategix.gbp.entity.MultiFactorRequest;
import com.costrategix.gbp.entity.User;
import com.costrategix.gbp.repository.MultiFactorRequestRepo;
import com.costrategix.gbp.repository.UserRepo;
import com.costrategix.gbp.service.ForgetPasswordService;

import net.bytebuddy.utility.RandomString;

@RestController
public class JwtController {
	
	@Autowired
    private JwtUtil jwtUtil;
	
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private ForgetPasswordService forgetPasswordService;
    
    @Autowired
    private MultiFactorRequestRepo multiFactorRequestRepo;
    
    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private Email send_email;
    
    @PostMapping("/authenticate")
    public Map<String,String> generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        System.out.println(authRequest.getPassword());
        Map<String,String> map = new HashMap<>();
    	try {
    		
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmailAdress(), authRequest.getPassword())
            );
            if ( forgetPasswordService.Login(authRequest.getEmailAdress()) == null) {
            	User user = userRepo.findByEmailAdress(authRequest.getEmailAdress()).get();
            	System.out.println("USer should come");
            	MultiFactorRequest mfa = multiFactorRequestRepo.findByUserId(user.getId()).get();
            	String OTP = RandomString.make(6);
    			mfa.setCodevalue(OTP);
    			mfa.setCreatedtime(LocalDateTime.now());
    			mfa.setExpireddate(LocalDateTime.now().plusMinutes(30));
    			multiFactorRequestRepo.save(mfa);
    			send_email.sendmfaEmail(user,OTP,"Your mfa code",3);
    			map.put("token",jwtUtil.generateToken(authRequest.getEmailAdress()));
    			map.put("mfa required", "yes");
    			return map;
            }
        } catch (Exception ex) {
            throw new Exception("inavalid username/password");
        }
    	map.put("token",jwtUtil.generateToken(authRequest.getEmailAdress()));
		map.put("mfa required", "no");
		return map;
    }
}
