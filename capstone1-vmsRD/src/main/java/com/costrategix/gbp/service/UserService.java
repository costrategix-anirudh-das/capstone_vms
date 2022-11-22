package com.costrategix.gbp.service;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.costrategix.gbp.common.Email;
import com.costrategix.gbp.dto.UserResponseDto;
import com.costrategix.gbp.entity.Invite;
import com.costrategix.gbp.entity.Organizations;
import com.costrategix.gbp.entity.Roles;
import com.costrategix.gbp.entity.User;
import com.costrategix.gbp.entity.UserAccountStatus;
import com.costrategix.gbp.entity.location_group;
import com.costrategix.gbp.entity.subLocations;
import com.costrategix.gbp.repository.InviteRepo;
import com.costrategix.gbp.repository.OrganizationRepo;
import com.costrategix.gbp.repository.RoleRepo;
import com.costrategix.gbp.repository.UserAccountRepo;
import com.costrategix.gbp.repository.UserRepo;
import com.costrategix.gbp.repository.location_groupRepo;
import com.costrategix.gbp.repository.subLocationsRepo;

import net.bytebuddy.utility.RandomString;

@Service
public class UserService {
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	RoleRepo roleRepo;
	
	private static final Logger LOGGER = LogManager.getLogger(UserService.class);
	
	@Autowired
	UserAccountRepo acctRepo;

	@Autowired
	Email send_email;
	
	@Autowired
	private InviteRepo inviteRepo;
	
	@Autowired
	private OrganizationRepo repo;
	
	@Autowired
	private location_groupRepo locationGRepo;
	
	@Autowired
	private subLocationsRepo sublocationRepo;
	
	
	public UserResponseDto response(int id)
    {	

         User u = userRepo.findById(id).get();

          int org = u.getOrgid();

          Organizations orgs = repo.findById(org).get();
          UserResponseDto userResponse = new UserResponseDto(id, u.getFirstName(), u.getLastName(),
        		  u.getEmailAdress(), u.getJobTitle(), u.getLdt().toString() ,
        		  orgs, u.getUserAccountStatus() , u.getRoless(),
        		  u.getPhoneNumber(), u.getUserLocationgroup(), u.getUsersubLocationgroup());
          return userResponse;
    }
	
	
	public String token_gen() {
		LOGGER.info("Generating token in UserService(token_gen)");
		String token = RandomString.make(30);
		Invite in=null;
		try {
			LOGGER.info("Finding by invite token in UserService(token_gen)");
			 in = inviteRepo.findByInviteToken(token).get();
			 return token_gen();
		}catch(NoSuchElementException e) {
			LOGGER.warn("No Such token in db UserService(token_gen): ",token);
			return token;
		}
	}
	
	public User add_user(User user) throws UnsupportedEncodingException, MessagingException {
		LOGGER.info("Finding by user role id in UserServices(add_user)");
		for(int i:user.getRole_id()) {
			Optional<Roles> R = roleRepo.findById(i);
			user.getRoless().add(R.get());
		}
		
		for(int i:user.getGroupLocationId()) {
			location_group lg=locationGRepo.findById(i).get();
			user.getUserLocationgroup().add(lg);
		}

		for(int i:user.getSubLocationId()) {
			subLocations sl=sublocationRepo.findById(i).get();
			user.getUsersubLocationgroup().add(sl);
		}

		
		//user.setSecurityHash(PasswordEncryption.encrypt(user.getSecurityHash()));
		Optional<UserAccountStatus> status = acctRepo.findById(user.getStatusId());
		user.setUserAccountStatus(status.get());

		String email = user.getEmailAdress();
		String phn = user.getPhoneNumber();
		
		System.out.println(user.getId());
		
		if(email.matches("[A-Za-z0-9._%+-]+@[A-Za-z]+\\.[a-z]{2,6}")
				&&((phn==null)||phn.matches("^(\\+91[\\-\\s]?)?[0]?(91)?[789]\\d{9}$"))) 
		{	
			LOGGER.info("Saving user id : ", user.getId()," in UserService(add_user)");
			userRepo.save(user);
			//String token = RandomString.make(30);
			String token = token_gen();
			System.out.println("valid");
			LOGGER.info("Finding user by email in UserService(add_user)");
			User newUser = userRepo.findByEmailAdress(email).get();
			Invite invite = new Invite(email,token,user.getOrgid(),newUser.getId(),0);
			inviteRepo.save(invite);
			//LocalDateTime.now().range()
			if(user.getLdt().getHour()>=10 && user.getLdt().getHour()<16) {
				String RegisterLink ="RegisterLink/?token=" + token;
				send_email.sendEmail(email, RegisterLink,1,newUser.getFirstName(),newUser.getLastName());
				LOGGER.info("Sending mail successful from UserService(add_user)");
				invite.setMailStatus(1);
				inviteRepo.save(invite);
				
			}
			return user;
		}
		
		else {
			System.out.println("Wrong Credentials");
			LOGGER.warn("Wrong Credentials");
			return null;
		}
	}
	
	@Transactional
	public User validate_token(String token,String email) throws UnsupportedEncodingException, MessagingException {
		Invite person=null;
		User user =null;
		
		try {
			LOGGER.info("Finding by Invite Token :",token," in UserService(validate_token)");
			person =  inviteRepo.findByInviteToken(token).get();
			if(person==null) {
				throw new Exception();
			}
			int id = person.getUserId();
			user =  userRepo.findById(id).get();
			if(!user.getEmailAdress().equals(email)) {
				return null;
			}
			
		}catch(Exception e) {
			LOGGER.warn("Token doesnt exist : ",token);
			return null;
		}
		
		if(person.getExpireDateTime().compareTo(LocalDateTime.now())>0) {
			LOGGER.info("Removing Token : ",token);
			inviteRepo.removeByUserId(person.getUserId());
			return user;
		}
		else {
			//String token1 = RandomString.make(30);
			String token1 = token_gen();
			String RegisterLink ="RegisterLink/?token=" + token1;
			send_email.sendEmail(person.getEmailAddress(), RegisterLink,1,user.getFirstName(),user.getLastName());
			return user;
		}
	}
	
	@CachePut(key="#user.id",value="User")
	public User send_users_update(User user) {
		User RepoObject =  userRepo.findById(user.getId()).get();
        if(RepoObject==null) {
            return null;
        }
        String phn = user.getPhoneNumber();
        User user_update = user;
        user_update.setSecurityHash(RepoObject.getSecurityHash());
        user_update.setEmailAdress(RepoObject.getEmailAdress());
        user_update.setLdt(RepoObject.getLdt());
        
        if(phn.matches("^(\\+91[\\-\\s]?)?[0]?(91)?[789]\\d{9}$")==true) {
        	user_update.setPhoneNumber(phn);
        }
        else {
        	user_update.setPhoneNumber(RepoObject.getPhoneNumber());
       }
        System.out.println("Here:"+user_update);
        user_update.getRoless().clear();
        
        for(int i:user.getRole_id()) {
            Optional<Roles> R = roleRepo.findById(i);
            user_update.getRoless().add(R.get());
        }
        
        for(int i:user.getGroupLocationId()) {
            location_group lg=locationGRepo.findById(i).get();
            user.getUserLocationgroup().add(lg);
        }

        for(int i:user.getSubLocationId()) {
            subLocations sl=sublocationRepo.findById(i).get();
            user.getUsersubLocationgroup().add(sl);
        }
        
        Optional<UserAccountStatus> status = acctRepo.findById(user.getStatusId());
        user_update.setUserAccountStatus(status.get());
        userRepo.save(user_update);
        return user_update;
    
    }
	

	@Cacheable(key= "#id", value = "User")
	public UserResponseDto getItem(int id){
		 UserResponseDto result = response(id);
		 return result;

	}
	
	@Cacheable(key= "#ord_id", value = "User2")
	public List<UserResponseDto> getusers(int org_id){		
		 List<User> users = userRepo.findUserByOrgid(org_id);
		 List<UserResponseDto> result = new ArrayList<>();
		 for (User user: users)
		 {
			 
			 result.add(response(user.getId()));
		 }
		 
		 return result;
	}
	
}
