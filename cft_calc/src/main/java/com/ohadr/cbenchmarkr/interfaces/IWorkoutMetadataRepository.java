package com.ohadr.cbenchmarkr.interfaces;

import java.util.Collection;
import java.util.Set;

import com.ohadr.cbenchmarkr.WorkoutMetadata;

public interface IWorkoutMetadataRepository
{
	/**
	 * get all workouts that are in the system.
	 * @return set of all workouts' names.
	 */
	Set<String> getAllWorkoutsNames();
	
	/**
	 * get all workouts (including metadata) that are in the system.
	 * @return set of all workouts' metadata
	 */
	Collection<WorkoutMetadata> getAllWorkoutsMetadata();

	/**
	 * add a new workout to the system.
	 * @param workoutName
	 */
	void addWorkoutMetadata(WorkoutMetadata workoutMetadata);

}
