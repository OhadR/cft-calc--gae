package com.ohadr.cbenchmarkr.core;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;

import com.ohadr.auth_flows.mocks.InMemoryAuthenticationUserImpl;
import com.ohadr.cbenchmarkr.interfaces.BenchmarkrAuthenticationUser;

public class BenchmarkrAuthenticationUserImpl extends
		InMemoryAuthenticationUserImpl implements BenchmarkrAuthenticationUser 
{
	private boolean 	isMale;
	private Date 		dateOfBirth;
	
	public BenchmarkrAuthenticationUserImpl(String username,
			String password,
			boolean activated, 
			int loginAttemptsLeft,
			Date passwordLastChangeDate, 
			String firstName, 
			String lastName,
			Collection<? extends GrantedAuthority> authorities,
			boolean isMale,
			Date dateOfBirth) 
	{
		super(username, password, activated, loginAttemptsLeft, passwordLastChangeDate,
				firstName, lastName, authorities);
		
		this.isMale = isMale;
		this.dateOfBirth = dateOfBirth;
	}

	@Override
	public boolean isMale()
	{
		return isMale;
	}

	@Override
	public Date getDateOfBirth() 
	{
		return dateOfBirth;
	}

}
