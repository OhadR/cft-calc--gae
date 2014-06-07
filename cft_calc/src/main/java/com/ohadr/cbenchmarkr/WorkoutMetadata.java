package com.ohadr.cbenchmarkr;

public class WorkoutMetadata
{
	private String    name;
	private String    description;
	
	/**
	 * workout can be reps-based (count number of perititions) or time-based (cound seconds)
	 * this member is true if the workout is rep-based.
	 */
	private boolean	  repititionBased;

	
	public WorkoutMetadata(String name, 
			String description,
			boolean repititionBased)
	{
		this.name = name;
		this.description = description;
		this.repititionBased = repititionBased;		
	}
	
	public String getName()
	{
		return name;
	}

/*	public void setName(String name)
	{
		this.name = name;
	}
*/
	public String getDescription()
	{
		return description;
	}

/*	public void setDescription(String description)
	{
		this.description = description;
	}
*/
	/**
	 * @return true if the workout is rep-based.
	 */
	public boolean isRepititionBased()
	{
		return repititionBased;
	}

/*	public void setRepititionBased(boolean repititionBased)
	{
		this.repititionBased = repititionBased;
	}
*/
}
