package com.ohadr.cbenchmarkr.interfaces;

import java.util.Date;

import com.ohadr.auth_flows.interfaces.AuthenticationUser;

public interface BenchmarkrAuthenticationUser extends AuthenticationUser 
{
	boolean isMale();
	Date 	getDateOfBirth();
	Date	getLastLoginDate();
}
