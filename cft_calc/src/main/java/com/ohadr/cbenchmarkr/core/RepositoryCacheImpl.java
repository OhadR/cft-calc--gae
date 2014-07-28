package com.ohadr.cbenchmarkr.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ohadr.cbenchmarkr.BenchmarkrRuntimeException;
import com.ohadr.cbenchmarkr.Workout;
import com.ohadr.cbenchmarkr.interfaces.IRepository;
import com.ohadr.cbenchmarkr.interfaces.ITrainee;
import com.ohadr.cbenchmarkr.utils.TimedResult;

@Component
public class RepositoryCacheImpl implements IRepository
{
	private static Logger log = Logger.getLogger(RepositoryCacheImpl.class);

	private Map<String, ITrainee> 	trainees = null;

	@Autowired
	@Qualifier("GAERepositoryImpl")
	private IRepository		repository;

	@Override
	public List<ITrainee> getAllTrainees() 
	{
		//usually 'values()' is of type HashMap.Values. so we need to convert, in order to sort, later on:
		List<ITrainee> retVal = new ArrayList<ITrainee>();
		for( ITrainee trainee : getTrainees().values() )
		{
			retVal.add( trainee );
		}
		return retVal;
	}


	private Map<String, ITrainee> loadTraineesFromDB() 
	{
		log.info("loading cache from DB");

		Map<String, ITrainee> retVal = new HashMap<String, ITrainee>();
		Collection<ITrainee> repoResult = repository.getAllTrainees();
		for( ITrainee trainee : repoResult )
		{
			retVal.put(trainee.getId(), trainee);
		}

		return retVal;
	}


	@Override
	public void addPerson(ITrainee person) 
	{
		//  Auto-generated method stub

	}

	@Override
	public void addWorkoutForTrainee(String trainee, Workout workout)
			throws BenchmarkrRuntimeException 
	{
		repository.addWorkoutForTrainee(trainee, workout);
		resetCache();
	}


	private void resetCache() 
	{
		log.info("resetting cache");
		trainees = null;
	}

	/**
	 * this impl is dependent on Trainee impl. In contrary to GEAImpl, where the 
	 * data is read from DB and directly sent back to manager
	 */
	@Override
	public List<TimedResult> getWorkoutHistoryForTrainee(String traineeId,
			String workoutName) 
	{
		log.debug("getWorkoutHistoryForTrainee for " + traineeId + ", " + workoutName);
		ITrainee trainee = getTrainees().get( traineeId );
		Map<String, List<TimedResult>> history = trainee.getHistory();
		if( history == null )
		{
			history = loadTraineesHistoryFromDB( traineeId );
			trainee.setHistory( history );
		}
		List<TimedResult> timedResults = history.get( workoutName );
		return timedResults;
	}

	private Map<String, List<TimedResult>> loadTraineesHistoryFromDB( String traineeId )
	{
		log.info("loading history from DB");
		
		Map<String, List<TimedResult>> repoResult = repository.getHistoryForTrainee( traineeId );

		return repoResult;
		
	}

	@Override
	public void updateGradesForTrainees(Map<String, Double> gradesPerTrainee)
			throws BenchmarkrRuntimeException 
	{
		log.debug("updating GradesForTrainees");
		repository.updateGradesForTrainees( gradesPerTrainee );
		resetCache();
	}

	@Override
	public void setAdmin(String authenticatedUsername) 
	{
		repository.setAdmin( authenticatedUsername );
	}

	@Override
	public int getNumberOfRegisteredUsers() 
	{
		return getTrainees().size();
	}


	private synchronized Map<String, ITrainee> getTrainees()
	{
		if( trainees == null )
		{
			trainees = loadTraineesFromDB();
		}
		return trainees;
	}

	

	@Override
	public Map<String, List<TimedResult>> getHistoryForTrainee(String traineeId) 
	{
		log.debug( "getHistoryForTrainee for " + traineeId );
		ITrainee trainee = getTrainees().get( traineeId );
		Map<String, List<TimedResult>> history = trainee.getHistory();
		if( history == null )
		{
			history = loadTraineesHistoryFromDB( traineeId );
			trainee.setHistory( history );
		}
		return history;
	}

}
