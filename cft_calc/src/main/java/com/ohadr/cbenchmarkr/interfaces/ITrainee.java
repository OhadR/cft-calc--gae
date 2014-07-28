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
	@Deprecated
	List<TimedResult> getWorkoutHistory(String workoutName);

	/**
	 * 
	 * @return the history table for the trainee: map from WOD-name to list of TimedResults.
	 */
	Map< String, List<TimedResult> > getHistory();

	void setHistory( Map<String, List<TimedResult>> history );

	/**
	 * TODO: this method never called. it supposed to reflect a user, but currently all info get through the Manager
	 */
	@Deprecated
	void addWorkout(Workout workout);

//	void setTotalGrade(double grade);

	double getTotalGrade();
	
	String getFirstName();
	
	String getLastName();

}
