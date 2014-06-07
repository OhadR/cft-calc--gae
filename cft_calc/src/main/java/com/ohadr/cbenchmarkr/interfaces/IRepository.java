package com.ohadr.cbenchmarkr.interfaces;

import java.util.*;

import com.ohadr.cbenchmarkr.Workout;

public interface IRepository
{
	Collection<IPerson> getAllTrainees();

	/**
	 * get all workouts 
	 * @return
	 */
	List<Workout> getAllWorkouts();

	/**
	 * when a new user activates his new account, call this method and add a new entry for this user
	 * @param personA
	 */
	void addPerson(IPerson person);

	void addWorkoutForTrainee(String trainee, Workout workout);

	
}
