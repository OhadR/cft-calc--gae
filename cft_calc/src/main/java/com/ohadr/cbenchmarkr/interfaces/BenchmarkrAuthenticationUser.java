package com.ohadr.cbenchmarkr.interfaces;

import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;

public interface BenchmarkrAuthenticationUser extends UserDetails 
{
	boolean isMale();
	Date 	getDateOfBirth();
	Date	getLastLoginDate();
	
	String getFirstName();
	String getLastName();

}
