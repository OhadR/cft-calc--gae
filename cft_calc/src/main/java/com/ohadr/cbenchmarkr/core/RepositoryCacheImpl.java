package com.ohadr.cbenchmarkr.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ohadr.cbenchmarkr.BenchmarkrRuntimeException;
import com.ohadr.cbenchmarkr.Trainee;
import com.ohadr.cbenchmarkr.Workout;
import com.ohadr.cbenchmarkr.interfaces.ICacheRepository;
import com.ohadr.cbenchmarkr.interfaces.IRepository;
import com.ohadr.cbenchmarkr.interfaces.ITrainee;
import com.ohadr.cbenchmarkr.utils.TimedResult;

@Component
public class RepositoryCacheImpl implements ICacheRepository
{
	private static Logger log = Logger.getLogger(RepositoryCacheImpl.class);

	private Map<String, ITrainee> 	trainees = null;

	/**
	 * maps from WOD-name to its average:
	 * this map is in the repo (and not in "GradesCalculator"), so upon "add workout for trainee", i will not have to 
	 * recalc the averages, but instead i can use the already-calc'ed values. Once a day, i will re-calc 
	 * all averages and grades.
	 */
	private Map<String, Integer> averageGrades = new HashMap<String, Integer>();

	@Autowired
	@Qualifier("GAERepositoryImpl")
	private IRepository		repository;

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
	public Map<String, ITrainee> getTraineesCache()
	{
		return loadTrainees();
	}


	private Map<String, ITrainee> loadTraineesFromDB() 
	{
		log.info("loading cache from DB");

		Map<String, ITrainee> retVal = new HashMap<String, ITrainee>();
		Collection<ITrainee> repoResult = repository.getTrainees();
		for( ITrainee trainee : repoResult )
		{
			retVal.put(trainee.getId(), trainee);
		}

		return retVal;
	}


	@Override
	public void addWorkoutForTrainee(String traineeId, Workout workout) throws BenchmarkrRuntimeException 
	{
		//add the workout both to "user" table, and to the "hostory" table. If @workout already exist, exception raised:
		repository.addWorkoutForTrainee(traineeId, workout);
		
		//	issue #47: in new impl, i do not reset cache (originally it was in order to re-load all data). i update
		//	the new workout, and keep it in cache, and when time come, i will re-calc all averages+grades. 
		//		resetCache();
		
		ITrainee trainee = getTraineesCache().get( traineeId );
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
		Map<String, List<TimedResult>> history = getHistoryForTrainee( traineeId );
		if( history == null )
			return null;
		
		List<TimedResult> timedResults = history.get( workoutName );
		return timedResults;
	}


	@Override
	public Map<String, List<TimedResult>> getHistoryForTrainee(String traineeId) 
	{
		log.debug( "getHistoryForTrainee for " + traineeId );
		ITrainee trainee = loadTrainees().get( traineeId );
		if( trainee == null )		//in case trainee has not enter any WOD-result (issue #52)
			return null;

		Map<String, List<TimedResult>> history = loadTraineesHistoryFromDB( traineeId );
		return history;
	}
	
	/**
	 * 
	 * @param traineeId
	 * @return: the history-map for this trainee. null if user has not entered any WOD.
	 */
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
			ITrainee trainee = loadTrainees().get( entry.getKey() );
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


	/**
	 * @return the trainees map that in the cache. if cache is empty, it loads the data from the DB
	 */
	private synchronized Map<String, ITrainee> loadTrainees()
	{
		if( trainees == null )
		{
			trainees = loadTraineesFromDB();
		}
		return trainees;
	}


	@Override
	public void createBenchmarkrAccount(String traineeId, String firstName,
			String lastName, boolean isMale, Date dateOfBirth)
			throws BenchmarkrRuntimeException
	{
		//enrich the auth-flows with the "isMale" and "DOB":
		repository.createBenchmarkrAccount(traineeId, firstName, lastName, isMale, dateOfBirth);
		
		//since issue #42+#47 (quota), we do not reset-cache and then reload data from DB. instead, create a new @Trainee objkect and put in cache:
		Map<String, Integer> results = new HashMap<String, Integer>();
		double totalGrade = 0;
		ITrainee trainee = new Trainee(traineeId, firstName, lastName, results, totalGrade, isMale, dateOfBirth);
		log.info("adding " + traineeId + " to cache");
		loadTrainees().put( traineeId,  trainee );
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
		for( ITrainee trainee : loadTrainees().values() )
		{
			//for each trainee, iterate all WODs
			sum += trainee.getResultsMap().size();
		}
		return sum;
	}


	@Override
	public Map<String, Integer> getAveragesForWorkouts()
	{
		return averageGrades;
	}


	public void logAverages()
	{
		log.info("log averages");
		for( Entry<String, Integer> pair : averageGrades.entrySet() )
		{
			log.info( pair.getKey() + " : " + pair.getValue() );
		}		
	}
}
