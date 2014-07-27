package com.ohadr.cbenchmarkr.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

	private Map<String, ITrainee> 	trainees = new HashMap<String, ITrainee>();

	@Autowired
	private IRepository		repository;

	@Override
	public Collection<ITrainee> getAllTrainees() 
	{
		if( trainees == null )
		{
			trainees = loadTraineesFromDB();
		}
		return trainees.values();
	}


	private Map<String, ITrainee> loadTraineesFromDB() 
	{
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

	@Override
	public List<TimedResult> getWorkoutHistoryForTrainee(String traineeId,
			String workoutName) 
	{
		ITrainee trainee = trainees.get( traineeId );
		return trainee.getWorkoutHistory( workoutName );
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
		return trainees.size();
	}

}
