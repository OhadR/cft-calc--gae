package com.ohadr.cbenchmarkr;

import java.util.*;

import org.apache.log4j.Logger;

import com.ohadr.cbenchmarkr.interfaces.IPerson;

public class Trainee implements IPerson
{
	private static Logger log = Logger.getLogger(Trainee.class);

	private String                      id;		//id == the name/email of the person
	private boolean   					bMale;
	private Date 	  					dateOfBirth;
	
	/**
	 * maps from WOD-name to the list of WODS the person did:
	 */
	private Map<String, List<Workout>>  history;
	
	/**
	 * maps from WOD-name to the results:
	 */
	private Map<String, Integer>  results;
	
	/**
	 * calculation of grade: per each workout, get the 'distance' from the average. Then calc the 
	 * average of distances. Why? because if PersonA did 3 workouts with diff=1 per each, the sum is 3. and personB
	 * did only 1 with distance=3, the sum is 3 as well. But personB is much better. so calc'ing average 
	 * gives a better result.
	 */
	private double 						totalGrade;

	public Trainee(String id)
	{
		this.id = id;
		history = new HashMap<String, List<Workout>>();
		results = new HashMap<String, Integer>();
		
	}
	
	/**
	 * called when item (entity) is loaded from the DB
	 * @param id
	 * @param results
	 */
	public Trainee(String id, Map<String, Integer> results)
	{
		this.id = id;
		history = new HashMap<String, List<Workout>>();
		this.results = results;
		
	}

	public String getId()
	{
		return id;
	}

	public Map<String, Integer> getResultsMap()
	{
		return results;
	}

	public void addWorkout(Workout workout)
	{
		log.info("adding workout " + workout);
		List<Workout> workouts = history.get( workout.getName() );
		if( workouts == null )
		{
			workouts = new ArrayList<Workout>();
			history.put( workout.getName(), workouts );			
		}
		workouts.add( workout );
		
		results.put( workout.getName(), workout.getResult() );			
	}

	public double getTotalGrade()
	{
		return totalGrade;
	}

	public void setTotalGrade(double totalGrade)
	{
		this.totalGrade = totalGrade;
	}

	/**
	 * we comare two trainees by their total-grade:
	 */
	@Override
	public int compareTo(IPerson o)
	{
		double comparison = totalGrade - o.getTotalGrade();
		if( comparison > 0 )
		{
			return 1;		//comparison can be 0.01
		}
		if( comparison < 0 )
		{
			return -1;
		}
		return 0;
	}
}
