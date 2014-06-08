package com.ohadr.cftcalc.tests;

import java.util.ArrayList;

import org.junit.Test;

import com.ohadr.cbenchmarkr.utils.TimedResult;

public class ParserTester
{
	@Test
	public void testParse()
	{
		String property = "1402071776642~14-1402071777642~14-1402071777642~15";

		//parse the string:
		String[] workouts = property.split( "-" );
		for(int i =0; i < workouts.length ; i++)
		//for(String workout : workouts)
		{
//			log.debug("******* Workout " + workouts[i]);
			String[] pair = workouts[i].split( "~" );
			TimedResult tr = new TimedResult( Integer.valueOf(pair[1]), Long.valueOf(pair[0]) );
//			retVal.add(  tr );
		}			
	}

}
