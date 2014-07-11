package com.ohadr.cbenchmarkr.interfaces;

import java.util.Map;

import com.ohadr.cbenchmarkr.Workout;

public interface ITrainee extends Comparable<ITrainee>
{
	String getId();

	/**
	 * maps from WOD-name to the results:
	 */
	Map<String, Integer> getResultsMap();
	
	void addWorkout(Workout workout);

//	void setTotalGrade(double grade);

	double getTotalGrade();
	
	String getFirstName();
	
	String getLastName();

}
