package com.ohadr.cbenchmarkr;

import java.util.*;

import org.springframework.stereotype.Component;

import com.ohadr.cbenchmarkr.interfaces.IWorkoutMetadataRepository;

@Component
public class WorkoutMetadataContainer implements IWorkoutMetadataRepository
{
	private Map<String, WorkoutMetadata> workouts = new HashMap<String, WorkoutMetadata>();
	
	
	public WorkoutMetadata getWorkoutMetadataByName(String workoutName)
	{
		return workouts.get( workoutName );
	}

	@Override
	public Set<String> getAllWorkoutsNames()
	{
		return workouts.keySet();
	}

	@Override
	public void addWorkoutMetadata(WorkoutMetadata workoutMetadata)
	{
		workouts.put( workoutMetadata.getName(), workoutMetadata );
	}


	
	
	/*********************** workouts table in GAE ************************ /
	public Set<String> getAllWorkoutsNames()
	{
		log.debug("getAllWorkoutsNames()");
		Query q = new Query(WORKOUTS_DB_KIND);
		PreparedQuery pq = datastore.prepare(q);  

		Set<String> retVal = new HashSet<String>();
		
		for (Entity entity : pq.asIterable()) 
		{
			String workoutName = (String) entity.getKey().getName();

			retVal.add( workoutName );
		}		

		return retVal;
	}

	public void addWorkoutMetadata(String workoutName) 
	{
		Entity workoutEntity = new Entity(WORKOUTS_DB_KIND, workoutName );
		datastore.put(workoutEntity);
		
	}*/
}
