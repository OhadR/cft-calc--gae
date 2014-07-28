package com.ohadr.cbenchmarkr.interfaces;

import java.util.*;

import com.ohadr.cbenchmarkr.BenchmarkrRuntimeException;
import com.ohadr.cbenchmarkr.Workout;
import com.ohadr.cbenchmarkr.utils.TimedResult;

public interface IRepository
{
	List<ITrainee> getAllTrainees();

	/**
	 * when a new user activates his new account, call this method and add a new entry for this user
	 * @param personA
	 */
	void addPerson(ITrainee person);

	void addWorkoutForTrainee(String trainee, Workout workout) throws BenchmarkrRuntimeException;
	
	/**
	 * 
	 * @param trainee
	 * @param workoutName: the requested workout's name.
	 * @return list of TimedResult elements. Per the requested workout, each element reperesents the date and the result.
	 */
	List<TimedResult> getWorkoutHistoryForTrainee(String trainee, String workoutName);

	/**
	 * 
	 * @param trainee
	 * @return map from WOD-name, to a list of TimedResult elements. For each 
	 * workout, each element reperesents the date and the result.
	 */
	Map< String, List<TimedResult> > getHistoryForTrainee( String traineeId );

	void updateGradesForTrainees( Map<String, Double> gradesPerTrainee ) throws BenchmarkrRuntimeException;

	void setAdmin(String authenticatedUsername);

	int getNumberOfRegisteredUsers();
	
}
