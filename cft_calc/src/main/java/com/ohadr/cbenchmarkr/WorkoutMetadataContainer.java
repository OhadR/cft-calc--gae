package com.ohadr.cbenchmarkr;

import com.google.appengine.api.datastore.*;

import java.util.*;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ohadr.cbenchmarkr.interfaces.IWorkoutMetadataRepository;

@Component
public class WorkoutMetadataContainer implements IWorkoutMetadataRepository
{
	private static final String WORKOUTS_DB_KIND = "Workouts";
	private static final String DESCRIPTION_PROP_NAME = "description";
	private static final String IS_REPITITION_BASED_PROP_NAME = "isRepititionBased";

	private static Logger log = Logger.getLogger(WorkoutMetadataContainer.class);


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
		updateDB( workoutMetadata );
		//force refreshing of cache from DB:
		workouts = null;
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
			boolean isRepititionBased = (Boolean) entity.getProperty( IS_REPITITION_BASED_PROP_NAME );
			String workoutDescription = (String) entity.getProperty( DESCRIPTION_PROP_NAME );

			retVal.put( workoutName, new WorkoutMetadata( workoutName, workoutDescription, isRepititionBased ) );
		}		

		return retVal;
	}

	private void updateDB( WorkoutMetadata workoutMetadata ) 
	{
		log.info("updating DB with workout: " + workoutMetadata );
		
		Entity workoutEntity = new Entity( WORKOUTS_DB_KIND, workoutMetadata.getName() );
		workoutEntity.setProperty( IS_REPITITION_BASED_PROP_NAME, workoutMetadata.isRepetitionBased() );
		workoutEntity.setProperty( DESCRIPTION_PROP_NAME, workoutMetadata.getDescription() );
		datastore.put(workoutEntity);
		
	}
}
