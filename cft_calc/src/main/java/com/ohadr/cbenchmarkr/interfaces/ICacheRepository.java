package com.ohadr.cbenchmarkr.interfaces;

import java.util.Map;

public interface ICacheRepository extends IRepository
{
	/**
	 * 
	 * @return maps of averages for workouts: from workout to its average
	 */
	Map<String, Integer> getAveragesForWorkouts();
	
	/**
	 * returns the number of all workouts-results that were entered. Basically, iterate over all trainees,
	 * and for each one of them go to his results-map (NOTE: not to the history map) and count the number of items.
	 * @return the number of all workouts-results that were entered. 
	 */
	int getNumberOfRegisteredResults();
	
	/**
	 * @return the trainees map that in the cache. if cache is empty, it loads the data from the DB
	 */
	Map<String, ITrainee> 	getTraineesCache();
}
