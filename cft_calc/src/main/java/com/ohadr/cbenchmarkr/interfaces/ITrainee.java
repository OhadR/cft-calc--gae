package com.ohadr.cbenchmarkr.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ohadr.cbenchmarkr.BenchmarkrRuntimeException;
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
	 * NOTE: this method is called in "in-mem" mode, when using cache. 
	 * @throws BenchmarkrRuntimeException - if same workout in the same date already exist . 
	 */
	void addWorkout(Workout workout) throws BenchmarkrRuntimeException;

	void setTotalGrade(double grade);
	double getTotalGrade();
	
	String getFirstName();
	
	String getLastName();

	boolean isMale();
	
	Date getDateOfBirth();

}
