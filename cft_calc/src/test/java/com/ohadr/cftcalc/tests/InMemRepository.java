package com.ohadr.cftcalc.tests;

import java.util.*;

import org.apache.commons.lang.NotImplementedException;

import com.ohadr.cbenchmarkr.Workout;
import com.ohadr.cbenchmarkr.interfaces.IRepository;
import com.ohadr.cbenchmarkr.interfaces.ITrainee;
import com.ohadr.cbenchmarkr.utils.TimedResult;

public class InMemRepository implements IRepository
{
	private List<ITrainee> persons = new ArrayList<ITrainee>();

	public InMemRepository()
	{
		init();
	}
	
	private void init()
	{
	}

	public void addPerson(ITrainee person)
	{
		persons.add( person );
	}

	@Override
	public List<ITrainee> getAllTrainees()
	{
		return persons;
	}

	@Override
	public void addWorkoutForTrainee(String trainee, Workout workout)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<TimedResult> getWorkoutHistoryForTrainee(String trainee,
			String workoutName)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateGradesForTrainees(Map<String, Double> gradesPerTrainee)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAdmin(String authenticatedUsername) 
	{
		throw new NotImplementedException();
	}

	@Override
	public int getNumberOfRegisteredUsers()
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
