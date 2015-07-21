package com.ohadr.cbenchmarkr;

import java.util.*;

import org.apache.log4j.Logger;

import com.ohadr.cbenchmarkr.interfaces.ITrainee;

/**
 * NOTE: the Trainee object does NOT hold the history. Because we do not want to serialize all history. We do not want to load 
 * the history from DB if not necessary.
 * @author OhadR
 *
 */
public class Trainee implements ITrainee
{
	private static Logger log = Logger.getLogger(Trainee.class);

	private String                      id;		//id == the name/email of the person
	private boolean   					bMale;
	private Date 	  					dateOfBirth;
	
	private String firstName;
	private String lastName;
	
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

/*	public Trainee(String id)
	{
		this.id = id;
		history = new HashMap<String, List<Workout>>();
		results = new HashMap<String, Integer>();
		
	}
*/	
	/**
	 * called when item (entity) is loaded from the DB
	 * @param id
	 * @param firstName 
	 * @param lastName 
	 * @param results
	 * @param isMale - gender
	 * @param dateOfBirth 
	 */
	public Trainee(String id, 
			String firstName,
			String lastName, 
			Map<String, Integer> results,
			double totalGrade, 
			boolean isMale, 
			Date dateOfBirth)
	{
		this.id = id;
		this.results = results;
		this.totalGrade = totalGrade;
		this.firstName = firstName;
		this.lastName = lastName;
		this.bMale = isMale;
		this.dateOfBirth = dateOfBirth;
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public Map<String, Integer> getResultsMap()
	{
		return results;
	}


	@Override
	public void addWorkout(Workout workout) throws BenchmarkrRuntimeException
	{
		log.info("adding workout " + workout);
		
		results.put( workout.getName(), workout.getResult() );			
	}

	@Override
	public void removeWorkout(Workout workout)
			throws BenchmarkrRuntimeException
	{
		log.info("removing workout " + workout);
		
		results.remove( workout.getName() );			
	}

	
	@Override
	public double getTotalGrade()
	{
		return totalGrade;
	}

	/**
	 * we compare two trainees by their total-grade:
	 */
	@Override
	public int compareTo(ITrainee o)
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

	@Override
	public String getFirstName()
	{
		return firstName;
	}

	@Override
	public String getLastName()
	{
		return lastName;
	}

	@Override
	public boolean isMale()
	{
		return bMale;
	}

	@Override
	public Date getDateOfBirth() 
	{
		return dateOfBirth;
	}

	@Override
	public void setTotalGrade(double grade)
	{
		totalGrade = grade;
	}

	@Override
	public int getNumerOfWorkouts()
	{
		return this.results.size();
	}
}
