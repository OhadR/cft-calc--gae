package com.ohadr.cbenchmarkr.utils;

public class TimedResult
{
	public TimedResult(int result, long timestamp)
	{
		this.result = result;
		this.timestamp = timestamp;
	}
	
	public int result;
	public long timestamp;	//secs from 1970
	
	public String toString()
	{
		return result + "/" + timestamp;
	}
}
