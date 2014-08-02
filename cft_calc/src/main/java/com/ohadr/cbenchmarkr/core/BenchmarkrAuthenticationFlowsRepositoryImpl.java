package com.ohadr.cbenchmarkr.core;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.google.appengine.api.datastore.*;
import com.ohadr.auth_flows.core.gae.GAEAuthenticationAccountRepositoryImpl;
import com.ohadr.auth_flows.interfaces.AuthenticationUser;
import com.ohadr.cbenchmarkr.BenchmarkrRuntimeException;


public class BenchmarkrAuthenticationFlowsRepositoryImpl extends
		GAEAuthenticationAccountRepositoryImpl 
{
	private static Logger log = Logger.getLogger(BenchmarkrAuthenticationFlowsRepositoryImpl.class);

	public static final String GENDER_PROP_NAME = "isMale";
	public static final String DOB_PROP_NAME = "DOB";
	
	public void enrichAccount(String traineeId, boolean isMale, Date dateOfBirth) throws BenchmarkrRuntimeException 
	{
		Key userKey = KeyFactory.createKey(AUTH_FLOWS_USER_DB_KIND, traineeId);
		Entity entity;
		try 
		{
			entity = datastore.get(userKey);
			//log.debug("got entity of " + traineeId + ": " + entity);
		} 
		catch (EntityNotFoundException e) 
		{
			log.error("entity of " + traineeId + " not found");
			throw new BenchmarkrRuntimeException(e.getMessage());
		}

		entity.setProperty(GENDER_PROP_NAME, isMale);				
		entity.setProperty(DOB_PROP_NAME, dateOfBirth);				
		datastore.put(entity);	
	}
	
	/**
	 * upon loading an entry from auth-flows DB, and convert it to 'Trainee', we enrich it with 
	 * more info that is in the auth-flows table - data that was added by this app, not by auth-flows
	 * e.g. isMale, dateOfBirth etc.
	 * @param trainee
	 * @throws UsernameNotFoundException 
	 */
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException
	{
		UserDetails userDetails = super.loadUserByUsername( username );
		//i know the super is 'GAEAuthenticationAccountRepositoryImpl' which returns 'AuthenticationUser'
		AuthenticationUser authFlowsUser = (AuthenticationUser)userDetails;
		
		Key userKey = KeyFactory.createKey(AUTH_FLOWS_USER_DB_KIND, username);
		Entity entity;
		try 
		{
			entity = datastore.get(userKey);
			log.debug("got entity of " + username + ": " + entity);
		} 
		catch (EntityNotFoundException e) 
		{
			log.error("entity of " + username + " not found");
			throw new UsernameNotFoundException(username, e);
		}

		boolean isMale = true;
		if( entity.hasProperty( GENDER_PROP_NAME ) )
		{
			isMale = (Boolean) entity.getProperty( GENDER_PROP_NAME ) ;
			log.debug(username + "/isMale=" + isMale);
		}
		else
		{
			log.error("isMale not found for user " + username);
		}
		
		Date dateOfBirth = null;
		if( entity.hasProperty( DOB_PROP_NAME ) )
		{
			dateOfBirth =  (Date) entity.getProperty( DOB_PROP_NAME );
		}

		return new BenchmarkrAuthenticationUserImpl(
						username, 
						authFlowsUser.getPassword(),
						authFlowsUser.isEnabled(),
						authFlowsUser.getLoginAttemptsLeft(),
						authFlowsUser.getPasswordLastChangeDate(),
						authFlowsUser.getFirstName(),
						authFlowsUser.getLastName(),
						authFlowsUser.getAuthorities(),
						isMale,
						dateOfBirth);

	}
	
}
