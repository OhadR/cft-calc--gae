package com.ohadr.cftcalc.tests;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ohadr.cbenchmarkr.BenchmarkrRuntimeException;
import com.ohadr.cbenchmarkr.GradesCalculator;
import com.ohadr.cbenchmarkr.utils.Utils;

public class CalculatorTest
{

	@Test
	public void testAverage() throws BenchmarkrRuntimeException
	{
		//TODO fix the test !!
	   	ApplicationContext context = 
	             new ClassPathXmlApplicationContext("spring-servlet.xml");
	 
	   	GradesCalculator calc = (GradesCalculator) context.getBean("gradesCalculator");

//		calc.calcAveragesAndGrades();

//		fail("Not yet implemented");
	}

	@Test
	public void testDoubleFormat()
	{
		double number = 30;
		double result = number / 7;
		result = Utils.formatDouble(result, 3);
		Assert.assertTrue( result == 4.285 );
		Assert.assertFalse( result == 4.28 );
	}
}
