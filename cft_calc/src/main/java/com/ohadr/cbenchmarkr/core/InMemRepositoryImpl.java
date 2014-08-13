package com.ohadr.cbenchmarkr.core;

import java.util.*;

import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

import com.ohadr.cbenchmarkr.BenchmarkrRuntimeException;
import com.ohadr.cbenchmarkr.Workout;
import com.ohadr.cbenchmarkr.interfaces.IRepository;
import com.ohadr.cbenchmarkr.interfaces.ITrainee;
import com.ohadr.cbenchmarkr.utils.TimedResult;

public class InMemRepositoryImpl implements IRepository
{
	private static Logger log = Logger.getLogger(InMemRepositoryImpl.class);

	private Map<String, ITrainee> 	trainees = null;

	private Map<String, List<TimedResult>> history;
	
	public InMemRepositoryImpl()
	{
	}
	

	@Override
	public List<ITrainee> getTrainees() 
	{
		//usually 'values()' is of type HashMap.Values. so we need to convert, in order to sort, later on:
		List<ITrainee> retVal = new ArrayList<ITrainee>();
		for( ITrainee trainee : loadTrainees().values() )
		{
			retVal.add( trainee );
		}
		return retVal;
	}

	@Override
	public ITrainee getTrainee(String traineeId)
	{
		return loadTrainees().get( traineeId );
	}


	private synchronized Map<String, ITrainee> loadTrainees()
	{
		if( trainees == null )
		{
			trainees = new HashMap<String, ITrainee>();
		}
		return trainees;
	}


	@Override
	public void addWorkoutForTrainee(String traineeId, Workout workout)
			throws BenchmarkrRuntimeException
	{
		ITrainee trainee = trainees.get( traineeId );
		trainee.addWorkout(workout);
	}

	@Override
	public List<TimedResult> getWorkoutHistoryForTrainee(String traineeId,
			String workoutName)
	{
		log.debug("getWorkoutHistoryForTrainee for " + traineeId + ", " + workoutName);

		Map<String, List<TimedResult>> history = getHistoryForTrainee( traineeId );
		
		List<TimedResult> timedResults = history.get( workoutName );
		return timedResults;
	}

	@Override
	public Map<String, List<TimedResult>> getHistoryForTrainee(String traineeId) 
	{
		log.debug( "getHistoryForTrainee for " + traineeId );

		if( history == null )
		{
			history = loadTraineesHistoryFromDB( traineeId );
		}
		return history;
	}

	private Map<String, List<TimedResult>> loadTraineesHistoryFromDB(
			String traineeId)
	{
		throw new NotImplementedException();
	}


	@Override
	public void updateGradesForTrainees(Map<String, Double> gradesPerTrainee)
	{
		for( Map.Entry<String, Double> entry : gradesPerTrainee.entrySet() )
		{
			ITrainee trainee = loadTrainees().get( entry.getKey() );
			trainee.setTotalGrade( entry.getValue() );
		}
	}

	@Override
	public void setAdmin(String authenticatedUsername) 
	{
		throw new NotImplementedException();
	}

	@Override
	public int getNumberOfRegisteredUsers()
	{
		return loadTrainees().size();
	}

	@Override
	public void createBenchmarkrAccount(String traineeId, boolean isMale, Date dateOfBirth)
			throws BenchmarkrRuntimeException
	{
		throw new NotImplementedException();
	}



	@Override
	public void resetRepository()
	{
		resetCache();
	}

	private void resetCache() 
	{
		log.info("resetting cache");
		trainees = null;
	}

//	@Override
	public int getNumberOfRegisteredResults()
	{
		int sum = 0;
		//iterate over all trainees
		for( ITrainee trainee : loadTrainees().values() )
		{
			//for each trainee, iterate all WODs
			sum += trainee.getResultsMap().size();
		}
		return sum;
	}


}
