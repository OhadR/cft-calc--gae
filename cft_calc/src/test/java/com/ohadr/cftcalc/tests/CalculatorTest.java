package com.ohadr.cftcalc.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ohadr.cbenchmarkr.GradesCalculator;

public class CalculatorTest
{

	@Test
	public void testAverage()
	{
		//TODO fix the test !!
	   	ApplicationContext context = 
	             new ClassPathXmlApplicationContext("spring-servlet.xml");
	 
	   	GradesCalculator calc = (GradesCalculator) context.getBean("gradesCalculator");

		calc.calcAverages();
		calc.calcGrades();
//		fail("Not yet implemented");
	}

}
