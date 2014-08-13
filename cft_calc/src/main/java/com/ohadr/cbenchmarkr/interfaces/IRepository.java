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
	 * @param lastName 
	 * @param firstName 
	 * @param isMale
	 * @param dateOfBirth
	 * @throws BenchmarkrRuntimeException 
	 */
	void createBenchmarkrAccount(
			String traineeId, 
			boolean isMale,
			Date dateOfBirth) throws BenchmarkrRuntimeException;
	
	List<ITrainee> 	getTrainees();
	ITrainee 		getTrainee( String traineeId );


	/**
	 * 
	 * @param traineeId
	 * @param workout
	 * @throws BenchmarkrRuntimeException - if same workout in the same date already exist. 
	 */
	void addWorkoutForTrainee(String traineeId, Workout workout) throws BenchmarkrRuntimeException;
	
	/**
	 * 
	 * @param trainee
	 * @param workoutName: the requested workout's name.
	 * @return list of TimedResult elements. Per the requested workout, each element reperesents the date and the result.
	 * null if no results were registered for this trainee.
	 */
	List<TimedResult> getWorkoutHistoryForTrainee(String trainee, String workoutName);

	/**
	 * 
	 * @param trainee
	 * @return map from WOD-name, to a list of TimedResult elements. For each 
	 * workout, each element reperesents the date and the result. 
	 * null if no results were registered for this trainee.
	 */
	Map< String, List<TimedResult> > getHistoryForTrainee( String traineeId );

	void updateGradesForTrainees( Map<String, Double> gradesPerTrainee ) throws BenchmarkrRuntimeException;

	void setAdmin(String authenticatedUsername);

	int getNumberOfRegisteredUsers();

	/**
	 * for TESTS PURPOSES ONLY
	 */
	void resetRepository();
}
