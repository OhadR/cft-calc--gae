package com.ohadr.cbenchmarkr.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public void addWorkoutForTrainee(String traineeId, Workout workout)
			throws BenchmarkrRuntimeException 
	{
		/**
		 * old impl: access repo.
		 *
		repository.addWorkoutForTrainee(trainee, workout);
		resetCache();
		*/
		ITrainee trainee = trainees.get( traineeId );
		trainee.addWorkout(workout);
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
		if( trainee == null )		//in case trainee has not enter any WOD-result (issue #52)
			return null;

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

	
	/**
	 * new impl: do not access repo when we update grades; instead use in mem.
	 */
	@Override
	public void updateGradesForTrainees(Map<String, Double> gradesPerTrainee)
			throws BenchmarkrRuntimeException 
	{
		log.debug("updating GradesForTrainees");
		/**
		 * old impl: update repo and reset cache.
		 * the drawback: the access to repo every time. meaning, every time a workout is updated, we get here
		 * and access the repo. we pay in performance and quota. 
		 *
			repository.updateGradesForTrainees( gradesPerTrainee );
			resetCache();
		*/
		
		for( Map.Entry<String, Double> entry : gradesPerTrainee.entrySet() )
		{
			ITrainee trainee = getTrainees().get( entry.getKey() );
			trainee.setTotalGrade( entry.getValue() );
		}
		
	}

	@Override
	public void setAdmin(String authenticatedUsername) 
	{
		repository.setAdmin( authenticatedUsername );
	}

	@Override
	public int getNumberOfRegisteredUsers() 
	{
//		return getTrainees().size();
		return repository.getNumberOfRegisteredUsers();
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


	@Override
	public void createBenchmarkrAccount(String traineeId, boolean isMale,
			Date dateOfBirth) throws BenchmarkrRuntimeException 
	{
		repository.createBenchmarkrAccount(traineeId, isMale, dateOfBirth);
	}


	@Override
	public void resetRepository()
	{
		resetCache();
		//delegate to GAE-repo:
		repository.resetRepository();
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
