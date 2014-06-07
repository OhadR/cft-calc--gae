package com.ohadr.cbenchmarkr.interfaces;

import java.util.Map;

import com.ohadr.cbenchmarkr.Workout;

public interface IPerson extends Comparable<IPerson>
{

	String getId();

	Map<String, Integer> getResultsMap();
	
	void addWorkout(Workout workout);

	void setTotalGrade(double grade);

	double getTotalGrade();

}
