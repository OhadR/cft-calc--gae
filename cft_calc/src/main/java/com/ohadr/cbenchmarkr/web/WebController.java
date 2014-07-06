package com.ohadr.cbenchmarkr.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ohadr.cbenchmarkr.BenchmarkrRuntimeException;
import com.ohadr.cbenchmarkr.Manager;
import com.ohadr.cbenchmarkr.Workout;
import com.ohadr.cbenchmarkr.WorkoutMetadata;
import com.ohadr.cbenchmarkr.interfaces.ITrainee;
import com.ohadr.cbenchmarkr.utils.TimedResult;
import com.ohadr.cbenchmarkr.utils.Utils;


@Controller
public class WebController 
{
	private static Logger log = Logger.getLogger(WebController.class);

    @Autowired
    private Manager manager;


    /**
     * 
     * @param name
     * @param result
     * @param date
     * @param response
     * @throws IOException 
     * @throws Exception
     */
    @RequestMapping(value = "/secured/addWorkoutForTrainee", method = RequestMethod.POST)
    protected void addWorkoutForTrainee(
    		@RequestParam("name")    String name,
    		@RequestParam("result")  int    result,
    		@RequestParam("date")    Date   date,
            HttpServletResponse response) throws IOException 
    {
    	PrintWriter writer = response.getWriter();
    	
    	Workout workout = new Workout( name, result );
    	workout.setDate( date );
        log.info( "adding workout: " + workout);

        try
        {
        	manager.addWorkoutForTrainee( getAuthenticatedUsername(), workout );
			manager.calcAveragesAndGrades();
        }
        catch (BenchmarkrRuntimeException be)
        {
            log.error( "error adding workout for user", be);
            writer.println( be.getMessage() );
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    		return;

        }

        response.setContentType("text/html"); 
		response.setStatus(HttpServletResponse.SC_OK);

    }

    
    /**
     * gets the 'latest' workouts from the repository. 
     * if trainee entered 3 results for the same workout (different dates) - he gets only the latest.
     * @param response
     */
    @RequestMapping(value = "/secured/getLatestWorkoutsForTrainee", method = RequestMethod.GET)
    protected void getLatestWorkoutsForTrainee(
            @RequestBody String json,
            HttpServletResponse response) 
    {
    	throw new NotImplementedException();
    }

    

    /**
     * 
     * @param json: workout name
     * TBD: pass the filter to this controller
     * @param response
     * @throws IOException 
     */
    @RequestMapping(value = "/secured/getWorkoutHistoryForTrainee", method = RequestMethod.GET)
    protected void getWorkoutHistoryForTrainee(
            @RequestParam String json,
            HttpServletResponse response) throws IOException 
    {  
    	PrintWriter writer = response.getWriter();

    	List<TimedResult> workouts = null;
		try
		{
			workouts = manager.getWorkoutHistoryForTrainee( getAuthenticatedUsername(), json);
		} 
		catch (BenchmarkrRuntimeException be)
		{
            log.error( "error getting history for user", be);
            writer.println( be.getMessage() );
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    		return;
		}
    	String jsonResponse = Utils.convertToJson( workouts );
    	
    	if( jsonResponse == null )
    	{
    		//represent an emptry object:
    		jsonResponse = "[]";
    	}
        
    	response.setContentType("text/html"); 
		response.setStatus(HttpServletResponse.SC_OK);

		writer.println( jsonResponse );
    }

    /**
     * TODO use filters 
     * @param json
     * @param response: json that represents the sorted list of trainees.
     * @throws IOException 
     */
    @RequestMapping(value = "/secured/getSortedTraineesByGrade", method = RequestMethod.GET)
    protected void getSortedTraineesByGrade(
            @RequestBody String json,
            HttpServletResponse response) throws IOException 
    {
    	Collection<ITrainee> trainees = manager.getSortedTraineesByGrade();
    	String jsonResponse = Utils.convertToJson( trainees );
    	response.getWriter().println( jsonResponse );
    }
    
    
    @RequestMapping(value = "/getAllWorkoutsNames", method = RequestMethod.GET)
    protected void getAllWorkoutsNames(
    		HttpServletResponse response) throws Exception
    {
    	Set<String> workouts = manager.getAllWorkoutsNames();
    	String jsonResponse = Utils.convertToJson( workouts );
    	response.getWriter().println( jsonResponse );    	
    }

    
    @RequestMapping("/ping")	
	protected void ping(
			HttpServletResponse response) throws Exception{
		log.info( "got to ping" );
		response.getWriter().println("ping response: pong");
	}
	
	@RequestMapping("/secured/ping")	
	protected void securedPing(
			HttpServletResponse response) throws Exception{
		log.info( "got to secured ping" );
		response.getWriter().println("secured ping response: pong");
	}


	private String getAuthenticatedUsername()
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName(); //get logged in username
		log.info( "logged in user: " + name );
		return name;
	}
	

	/**
	 * called by a cron job.
	 * @param request
	 * @return
	 * @throws IOException 
	 */
//	@RequestMapping("/calcAveragesAndGrades")
	protected void calcAveragesAndGrades(HttpServletResponse response) throws IOException
	{
		log.info( "calc averages and grades" );
    	PrintWriter writer = response.getWriter();

    	try
		{
			manager.calcAveragesAndGrades();
		} 
		catch (BenchmarkrRuntimeException be)
		{
            log.error( "error calcAveragesAndGrades", be);
            writer.println( be.getMessage() );
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    		return;
		}

        response.setContentType("text/html"); 
		response.setStatus(HttpServletResponse.SC_OK);
	}
}