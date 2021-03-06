package com.ohadr.cbenchmarkr.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class MailSenderWrapper
{
	@Autowired
	private MailSender			mailSender;

	
	public void notifyAdmin(String email, 
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
