package com.ohadr.cftcalc.tests;

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


	public InMemRepositoryImpl()
	{
		init();
	}
	

	private void init()
	{
	}

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

	private synchronized Map<String, ITrainee> getTrainees()
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
			ITrainee trainee = getTrainees().get( entry.getKey() );
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
		return getTrainees().size();
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

	@Override
	public void createBenchmarkrAccount(String traineeId, boolean isMale,
			Date dateOfBirth) 
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

	@Override
	public int getNumberOfRegisteredResults()
	{
		int sum = 0;
		//iterate over all trainees
		for( ITrainee trainee : getTrainees().values() )
		{
			//for each trainee, iterate all WODs
			sum += trainee.getResultsMap().size();
		}
		return sum;
	}


}
