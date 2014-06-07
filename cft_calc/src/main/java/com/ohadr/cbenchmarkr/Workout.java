package com.ohadr.cbenchmarkr;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;


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
	
	
	/**
     *  Create Workout instance out of Json
     *  Example for valid json:   {"date":1402071766642,"result":120,"name":"CINDY"}
     * @param json
     */
    static public Workout parseJson(String json) throws IOException 
    {
        ObjectMapper mapper = new ObjectMapper();
        
/*        //test
        ByteArrayOutputStream bis = new ByteArrayOutputStream();

        Workout dummy = new Workout("CINDY", 120);
        mapper.writeValue(bis, dummy);
        String jsonDummy = bis.toString();
        System.err.println( jsonDummy );

        mapper.setDateFormat(new DateFormat());
        */

        Workout workout = mapper.readValue(json, Workout.class);
        return workout;
    }
}
