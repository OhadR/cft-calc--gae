package com.ohadr.cbenchmarkr.core;

import java.util.Date;
import java.util.NoSuchElementException;

import org.apache.log4j.Logger;

import com.google.appengine.api.datastore.*;
import com.ohadr.auth_flows.core.gae.GAEAuthenticationAccountRepositoryImpl;
import com.ohadr.cbenchmarkr.BenchmarkrRuntimeException;


public class BenchmarkrAuthenticationFlowsRepositoryImpl extends
		GAEAuthenticationAccountRepositoryImpl 
{
	private static Logger log = Logger.getLogger(BenchmarkrAuthenticationFlowsRepositoryImpl.class);

	private static final String GENDER_PROP_NAME = "isMale";
	private static final String DOB_PROP_NAME = "DOB";
	
	public void enrichAccount(String traineeId, boolean isMale, Date dateOfBirth) throws BenchmarkrRuntimeException 
	{
		Key userKey = KeyFactory.createKey(AUTH_FLOWS_USER_DB_KIND, traineeId);
		Entity entity;
		try 
		{
			entity = datastore.get(userKey);
			log.debug("got entity of " + traineeId + ": " + entity);
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
}
