package com.ohadr.cftcalc.tests;

import java.awt.Window;
import java.util.*;

import com.ohadr.cftcalc.CftCalcConstants;
import com.ohadr.cftcalc.Person;
import com.ohadr.cftcalc.Workout;
import com.ohadr.cftcalc.interfaces.IPerson;
import com.ohadr.cftcalc.interfaces.IRepository;

public class InMemRepository implements IRepository
{
	private List<IPerson> persons = new ArrayList<IPerson>();

	private Set<String> workoutsNames = new HashSet<String>();

	public InMemRepository()
	{
		init();
	}
	
	private void init()
	{
	}

	public List<IPerson> getAllPersons()
	{
		return persons;
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

	public void addPerson(IPerson person)
	{
		persons.add( person );
	}

	public void addWorkout(String workout)
	{
		workoutsNames.add( workout );
	}

}
