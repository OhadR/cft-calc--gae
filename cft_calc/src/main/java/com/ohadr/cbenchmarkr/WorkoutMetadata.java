package com.ohadr.cbenchmarkr;


public class WorkoutMetadata
{
	private String    name;
	private String    description;
	
	/**
	 * workout can be reps-based (count number of perititions) or time-based (cound seconds)
	 * this member is true if the workout is rep-based.
	 */
	private boolean	  repetitionBased;

	
	//for serialization (from JSON) "JsonMappingException: No suitable constructor found"
	public WorkoutMetadata() {}
	
	public WorkoutMetadata(String name, 
			String description,
			boolean repetitionBased)
	{
		this.name = name;
		this.description = description;
		this.repetitionBased = repetitionBased;		
	}
	
	public String getName()
	{
		return name;
	}

	public String getDescription()
	{
		return description;
	}

	/**
	 * @return true if the workout is rep-based.
	 */
	public boolean isRepetitionBased()
	{
		return repetitionBased;
	}

	public String toString()
	{
		return name + ": repetitionBased? " + repetitionBased;
	}
}
