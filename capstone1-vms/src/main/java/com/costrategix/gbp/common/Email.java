package com.costrategix.gbp.common;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.aspectj.apache.bcel.classfile.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.costrategix.gbp.entity.EmailTemplate;
import com.costrategix.gbp.entity.User;
import com.costrategix.gbp.repository.EmailTemplateRepo;


@Component
public class Email {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private EmailTemplateRepo emailRepo;
	
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
}
