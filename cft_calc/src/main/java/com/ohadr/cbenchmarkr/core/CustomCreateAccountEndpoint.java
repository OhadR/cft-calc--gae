package com.ohadr.cbenchmarkr.core;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.ohadr.auth_flows.endpoints.CreateAccountEndpoint;
import com.ohadr.auth_flows.types.AuthenticationFlowsException;


@Component
public class CustomCreateAccountEndpoint extends CreateAccountEndpoint
{
	private static Logger log = Logger.getLogger(CustomCreateAccountEndpoint.class);

	@Autowired
	private MailSender			mailSender;

	public void additionalValidations(String email, String password) throws AuthenticationFlowsException 
	{
		log.info("Benchmarkr custom validations:");
		
		//make sure the email is lower-case:
		if( ! email.equals( email.toLowerCase() ) )
		{
			throw new AuthenticationFlowsException("accept only lower-case emails");			
		}
				
		
		super.additionalValidations(email, password);
	}
	
	
	public void postCreateAccount( String username )
	{
		log.info("this is a custom message from postCreateAccount - notify admin");
		
		notifyAdmin("ohad.redlich@gmail.com",
				"a new user registered to cBenchmarkr!",
				"a new user registered to cBenchmarkr: " + username);
		
		super.postCreateAccount( username );		
	}
	
	
	private void notifyAdmin(String email, 
			String mailSubject,
			String mailBody)
	{
        SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(email);
		msg.setSubject(mailSubject);
		msg.setText(mailBody);
		
		mailSender.send( msg );
	}



}
