package com.ohadr.cbenchmarkr.interfaces;

import java.util.*;

import com.ohadr.cbenchmarkr.BenchmarkrRuntimeException;
import com.ohadr.cbenchmarkr.Workout;
import com.ohadr.cbenchmarkr.utils.TimedResult;

public interface IRepository
{
	/**
	 * when a new user creates account, the UI is responsible to invoke a call - AFTER
	 * the account is created successfully - to this method.
	 * @param traineeId
	 * @param isMale
	 * @param dateOfBirth
	 */
	void createBenchmarkrAccount(
			String traineeId, 
			boolean isMale,
			Date dateOfBirth);
	
	List<ITrainee> getAllTrainees();

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
