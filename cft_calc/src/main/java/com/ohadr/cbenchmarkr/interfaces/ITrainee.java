package com.ohadr.cbenchmarkr.interfaces;

import java.util.Date;
import java.util.Map;

import com.ohadr.cbenchmarkr.BenchmarkrRuntimeException;
import com.ohadr.cbenchmarkr.Workout;

public interface ITrainee extends Comparable<ITrainee>
{
	String getId();

	/**
	 * maps from WOD-name to the results:
	 */
	Map<String, Integer> getResultsMap();
	
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
