package com.ohadr.cbenchmarkr.interfaces;

import java.util.Map;

import com.ohadr.cbenchmarkr.BenchmarkrRuntimeException;
import com.ohadr.cbenchmarkr.Workout;

public interface ITrainee extends Comparable<ITrainee>
{
	String getId();

	/**
	 * 
	 * @return map from WOD-name to the result.
	 */
	Map<String, Integer> getResultsMap();
	
	/**
	 * NOTE: this method is called in "in-mem" mode, when using cache. 
	 * @throws BenchmarkrRuntimeException - if same workout in the same date already exist. 
	 */
	void addWorkout(Workout workout) throws BenchmarkrRuntimeException;

	/**
	 * 
	 * @param workout
	 * @throws BenchmarkrRuntimeException - if workout does not exist for this trainee.
	 */
	void removeWorkout(Workout workout) throws BenchmarkrRuntimeException;

	void setTotalGrade(double grade);
	double getTotalGrade();
	
	String getFirstName();
	
	String getLastName();

	boolean isMale();
	
	/**
	 * 
	 * @return the # of WODs for this Trainee that his grade was calc'ed from. Not including history; meaning 
	 * if Trainee has entered 10 Deadlifts, we count only 1.
	 */
	int		getNumerOfWorkouts();

}
