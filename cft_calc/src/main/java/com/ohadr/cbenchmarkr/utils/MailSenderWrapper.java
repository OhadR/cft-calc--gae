package com.ohadr.cbenchmarkr.utils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

@Component
public class MailSenderWrapper
{
	@Autowired
	private MailSender			mailSender;

	@Autowired
    private VelocityEngine 		velocityEngine;

	
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
	
	
	public void sendMail(String email, 
			String mailSubject,
			String msgTextFileName,
			String urlInMessage)
	{
		Map<String, Object> model = new HashMap<String, Object>();
        model.put("username", email);
        model.put("url", urlInMessage);
        String mailBody = VelocityEngineUtils.mergeTemplateIntoString(
                velocityEngine, getResourcePath( msgTextFileName ), model);

        SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(email);
		msg.setSubject(mailSubject);
		msg.setText(mailBody);
		
		mailSender.send( msg );
	}
	
	private static String getResourcePath(String name)
	{
		return "mailTemplates/" + Locale.getDefault().getLanguage() + "/" + name;
	}




}
