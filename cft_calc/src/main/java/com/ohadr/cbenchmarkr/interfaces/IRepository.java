package com.ohadr.cbenchmarkr.interfaces;

import java.util.*;

import com.ohadr.cbenchmarkr.BenchmarkrRuntimeException;
import com.ohadr.cbenchmarkr.Workout;
import com.ohadr.cbenchmarkr.utils.TimedResult;

public interface IRepository
{
	Collection<ITrainee> getAllTrainees();

	/**
	 * get all workouts 
	 * @return
	 */
	List<Workout> getAllWorkouts();

	/**
	 * when a new user activates his new account, call this method and add a new entry for this user
	 * @param personA
	 */
	void addPerson(ITrainee person);

	void addWorkoutForTrainee(String trainee, Workout workout);
	
	/**
	 * 
	 * @param trainee
	 * @param workoutName: the requested workout's name.
	 * @return list of TimedResult elements. Per the requested workout, each element reperesents the date and the result.
	 */
	List<TimedResult> getWorkoutHistoryForTrainee(String trainee, String workoutName);
	
	void updateGradesForTrainees( Map<String, Double> gradesPerTrainee ) throws BenchmarkrRuntimeException;

	void setAdmin(String authenticatedUsername);
	
}
