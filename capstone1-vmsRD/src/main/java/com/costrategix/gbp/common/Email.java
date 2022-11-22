package com.costrategix.gbp.common;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.aspectj.apache.bcel.classfile.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.costrategix.gbp.entity.EmailTemplate;
import com.costrategix.gbp.entity.Invite;
import com.costrategix.gbp.entity.User;
import com.costrategix.gbp.repository.EmailTemplateRepo;
import com.costrategix.gbp.repository.InviteRepo;
import com.costrategix.gbp.repository.UserAccountRepo;
import com.costrategix.gbp.repository.UserRepo;

import net.bytebuddy.utility.RandomString;


@Component
@EnableAsync
public class Email {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private EmailTemplateRepo emailRepo;
	
	@Autowired
	private InviteRepo inviteRepo;

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private UserAccountRepo acctRepo; 
	
	public void sendEmail(String recipientEmail, String link,int template_id,String firstName,String LastName)
	        throws MessagingException, UnsupportedEncodingException {
		System.out.println(recipientEmail);
		System.out.println("Here I m");
	    MimeMessage message = mailSender.createMimeMessage();              
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	    
	    EmailTemplate template = emailRepo.findById(template_id).get();
	    helper.setTo(recipientEmail);
	    String body = template.getTemplateHTML();
	    body=body.replace("{First name}",firstName );
	    body=body.replace("{Last Name}",LastName);
	    body=body.replace("[Activation Link]", link);
	     
	    String content = body;
	     
	    helper.setSubject(template.getSubject());
	     
	    helper.setText(content, true);
	    System.out.println("Here I m");
	    mailSender.send(message);
	}
	
	public void sendmfaEmail(User user, String code,String subject,int template_id)
	        throws MessagingException, UnsupportedEncodingException {
		System.out.println(user.getEmailAdress());
		System.out.println("Here I m");
		System.out.println(mailSender);
	    MimeMessage message = mailSender.createMimeMessage();              
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	     	     
	    helper.setTo(user.getEmailAdress());
	    
	    //String subj = subject;
	     
	    
	    EmailTemplate template = emailRepo.findById(template_id).get();
	    helper.setTo(user.getEmailAdress());
	    String body = template.getTemplateHTML();
	    body=body.replace("{First name}",user.getFirstName());
	    body=body.replace("{Last Name}",user.getLastName());
	    body = body.replace("{MFA CODE}",code);
	    
	     
	    helper.setSubject(subject);
	     
	    helper.setText(body, true);
	 
	    mailSender.send(message);
	    
	}
	
	public String token_gen() {
		//LOGGER.info("Generating token in UserService(token_gen)");
		String token = RandomString.make(30);
		Invite in=null;
		try {
			//LOGGER.info("Finding by invite token in UserService(token_gen)");
			 in = inviteRepo.findByInviteToken(token).get();
			 return token_gen();
		}catch(NoSuchElementException e) {
			//LOGGER.warn("No Such token in db UserService(token_gen): ",token);
			return token;
		}
	}
	
	@Async
	@Scheduled(cron="0 */1 * * * *")
	public void scheduled_register_emails() throws UnsupportedEncodingException, MessagingException {
		Optional<List<Invite>> P;
		List<Invite> L;
		System.out.println("HERE HERE");
		try {
			
			P = inviteRepo.findByMailStatus(0);
			if(P.get().isEmpty()) {
				throw new Exception();
			}
			else {
				L = P.get();
				String token = token_gen();
				for(Invite i:L) {
					User u = userRepo.findById(i.getUserId()).get();
					String RegisterLink ="RegisterLink/?token=" + token;
					sendEmail(i.getEmailAddress(), RegisterLink,1,u.getFirstName(),u.getLastName());
					i.setMailStatus(1);
					inviteRepo.save(i);
			}
			}

		}
		catch(Exception e) {
			System.out.println("No Mails to send");
		}
		
	}
	
}
