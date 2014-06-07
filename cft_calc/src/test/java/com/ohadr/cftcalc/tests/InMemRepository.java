package com.ohadr.cftcalc.tests;

import java.util.*;

import com.ohadr.cbenchmarkr.Trainee;
import com.ohadr.cbenchmarkr.Workout;
import com.ohadr.cbenchmarkr.interfaces.IPerson;
import com.ohadr.cbenchmarkr.interfaces.IRepository;

public class InMemRepository implements IRepository
{
	private static final String ANNIE = "annie";
	private static final String BARBARA = "barbara";
	private static final String CINDY = "cindy";

	private Map<String, IPerson> persons = new HashMap<String, IPerson>();

	private Set<String> workoutsNames = new HashSet<String>();

	public InMemRepository()
	{
		init();
	}
	
	private void init()
	{
		IPerson personA = new Trainee("moshe");
		personA.addWorkout(new Workout(CINDY, 100));
		personA.addWorkout(new Workout(CINDY, 120));
		persons.put(personA.getId(), personA);
		
		IPerson personB = new Trainee("kookoo");
		personB.addWorkout(new Workout(CINDY, 100));
		personB.addWorkout(new Workout(BARBARA, 60));
		persons.put(personB.getId(), personB);
		
		
		workoutsNames.add(CINDY);
		workoutsNames.add(BARBARA);
		workoutsNames.add(ANNIE);
	}

	public Collection<IPerson> getAllTrainees()
	{
		return persons.values();
	}

	public List<Workout> getAllWorkouts()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Set<String> getAllWorkoutsNames()
	{
		return workoutsNames;
	}

	@Override
	public void addPerson(IPerson personA)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addWorkoutForTrainee(String trainee, Workout workout)
	{
		IPerson traineeEntry = persons.get( trainee );
		if( traineeEntry == null )
		{
			//error; this should never happen.
			throw new RuntimeException();			
		}
		traineeEntry.addWorkout( workout );
	}

}
