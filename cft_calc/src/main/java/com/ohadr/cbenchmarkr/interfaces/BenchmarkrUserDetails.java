package com.ohadr.cbenchmarkr.interfaces;

import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;

public interface BenchmarkrUserDetails extends UserDetails 
{
	boolean isMale();
	Date	getLastLoginDate();
	
	String getFirstName();
	String getLastName();

}
