package com.ohadr.cbenchmarkr.core;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.ohadr.auth_flows.endpoints.CreateAccountEndpoint;
import com.ohadr.auth_flows.types.AuthenticationFlowsException;
import com.ohadr.cbenchmarkr.utils.MailSenderWrapper;


@Component
public class CustomCreateAccountEndpoint extends CreateAccountEndpoint
{
	private static Logger log = Logger.getLogger(CustomCreateAccountEndpoint.class);
	
	@Autowired
	MailSenderWrapper mailSenderWrapper;

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
		
		mailSenderWrapper.notifyAdmin("ohad.redlich@gmail.com",
				"cBenchmarkr: new registered user",
				"a new user has registered to cBenchmarkr: " + username);
		
		super.postCreateAccount( username );		
	}
	
	
}
