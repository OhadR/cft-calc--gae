package com.ohadr.cbenchmarkr.interfaces;

import java.util.List;
import java.util.Map;

import com.ohadr.cbenchmarkr.Workout;
import com.ohadr.cbenchmarkr.utils.TimedResult;

public interface ITrainee extends Comparable<ITrainee>
{
	String getId();

	/**
	 * maps from WOD-name to the results:
	 */
	Map<String, Integer> getResultsMap();
	
	/**
	 * 
	 * @param workoutName - the workout that its history table is required
	 * @return - list of TimedResult objects for the required workout.
	 */
	List<TimedResult> getWorkoutHistory(String workoutName);

	/**
	 * TODO: this method never called. it supposed to reflect a user, but currently all info get through the Manager
	 */
	void addWorkout(Workout workout);

//	void setTotalGrade(double grade);

	double getTotalGrade();
	
	String getFirstName();
	
	String getLastName();

}
