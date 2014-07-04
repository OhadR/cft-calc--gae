package com.ohadr.cbenchmarkr;

import com.google.appengine.api.datastore.*;

import java.util.*;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ohadr.cbenchmarkr.interfaces.IWorkoutMetadataRepository;

@Component
public class WorkoutMetadataContainer implements IWorkoutMetadataRepository
{
	private static Logger log = Logger.getLogger(WorkoutMetadataContainer.class);

	private static final String WORKOUTS_DB_KIND = "Workouts";

	private DatastoreService datastore;

	//cache:
	private Map<String, WorkoutMetadata> workouts = null;
	
	
	public WorkoutMetadataContainer()
	{
		datastore = DatastoreServiceFactory.getDatastoreService();
	}
	
	private Map<String, WorkoutMetadata> getWorkouts()
	{
		if( workouts == null )
		{
			workouts = loadWorkoutsMetadataFromDB();
		}
		
		return workouts;
	}
	
	public WorkoutMetadata getWorkoutMetadataByName(String workoutName)
	{
		return getWorkouts().get( workoutName );
	}

	@Override
	public Set<String> getAllWorkoutsNames()
	{
		return getWorkouts().keySet();
	}

	@Override
	public void addWorkoutMetadata(WorkoutMetadata workoutMetadata)
	{
		getWorkouts().put( workoutMetadata.getName(), workoutMetadata );
		updateDB( workoutMetadata );
	}


	
	
	/*********************** workouts table in GAE ************************/
	private Map<String, WorkoutMetadata> loadWorkoutsMetadataFromDB()
	{
		log.debug("getting All Workouts From DB");
		Query q = new Query( WORKOUTS_DB_KIND );
		PreparedQuery pq = datastore.prepare(q);  

		Map<String, WorkoutMetadata> retVal = new HashMap<String, WorkoutMetadata>();
		
		for (Entity entity : pq.asIterable()) 
		{
			String workoutName = (String) entity.getKey().getName();
			boolean isRepititionBased = (Boolean) entity.getProperty( "isRepititionBased" );
			String workoutDescription = (String) entity.getProperty( "description" );

			retVal.put( workoutName, new WorkoutMetadata( workoutName, workoutDescription, isRepititionBased ) );
		}		

		return retVal;
	}

	private void updateDB( WorkoutMetadata workoutMetadata ) 
	{
		log.info("updating DB with workout: " + workoutMetadata );
		
		Entity workoutEntity = new Entity( WORKOUTS_DB_KIND, workoutMetadata.getName() );
		workoutEntity.setProperty( "isRepititionBased", workoutMetadata.isRepititionBased() );
		datastore.put(workoutEntity);
		
	}
}
