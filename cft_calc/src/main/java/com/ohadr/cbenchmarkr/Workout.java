package com.ohadr.cbenchmarkr;

import java.util.Date;

public class Workout
{
	private Date   date;

	private int    result;

	private String name;

	public Workout() {}		//for serialization
	
	public Workout(String name, int result, Date date)
	{
		this.name = name;
		this.result = result;
		this.date = date;
	}
	
	public Workout(String name, int result)
	{
		this( name, result, new Date( System.currentTimeMillis() ) );
	}

	public Date getDate()
	{
		return date;
	}
	public void setDate(Date date)
	{
		this.date = date;
	}
	public int getResult()
	{
		return result;
	}
	public void setResult(int result)
	{
		this.result = result;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	
	@Override
	public String toString()
	{
		return name + "/" + result + " (" + date + ")";
	}
}
