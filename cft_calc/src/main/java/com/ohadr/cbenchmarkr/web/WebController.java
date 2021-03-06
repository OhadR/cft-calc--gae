package com.ohadr.cbenchmarkr.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
     */
    @RequestMapping(value = "/secured/addWorkoutForTrainee", method = RequestMethod.POST)
    protected void addWorkoutForTrainee(
    		@RequestParam("name")    String name,
    		@RequestParam("result")  int    result,
//    		@RequestParam("date")    Date   date,
    		@RequestParam("date")    String   dateText,		//UI format: 1974-10-12
            HttpServletResponse response) throws IOException 
    {
    	PrintWriter writer = response.getWriter();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
		try
		{
			date = format.parse(dateText);
		} 
		catch (ParseException pe) //if date could not be parsed
		{
            log.error( "date could not be parsed; recieved: " + dateText, pe);
            date = new Date();
		}

    	Workout workout = new Workout( name, result );
    	workout.setDate( date );
        log.info( "adding workout: " + workout);

        try
        {
        	manager.addWorkoutForTrainee( Utils.getAuthenticatedUsername(), workout );
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
     * 
     * @param name
     * @param response
     * @throws IOException 
     */
    @RequestMapping(value = "/secured/removeWorkoutForTrainee", method = RequestMethod.POST)
    protected void removeWorkoutForTrainee(
    		@RequestParam("name")    String name,
            HttpServletResponse response) throws IOException 
    {
    	PrintWriter writer = response.getWriter();

    	Workout workout = new Workout( name, -1 );
        log.info( "removing workout: " + workout.getName());

        try
        {
        	manager.removeWorkoutForTrainee( Utils.getAuthenticatedUsername(), workout );
        }
        catch (BenchmarkrRuntimeException be)
        {
            log.error( "error removing workout for user", be);
            writer.println( be.getMessage() );
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    		return;

        }

        response.setContentType("text/html"); 
		response.setStatus(HttpServletResponse.SC_OK);

    }
    
    /**
     * 
     * @param json workout name
     * TBD: pass the filter to this controller
     * @param response json of the list of TimedResults workouts (results+dates). empty list if none exist. 
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
			workouts = manager.getWorkoutHistoryForTrainee( Utils.getAuthenticatedUsername(), json);
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
     * @param response: json that represents the sorted list of trainees.
     * @throws IOException 
     */
    @RequestMapping(value = "/secured/getSortedTraineesByGrade", method = RequestMethod.GET)
    protected void getSortedTraineesByGrade(
            HttpServletResponse response) throws IOException 
    {
    	Collection<ITrainee> trainees = manager.getSortedTraineesByGrade();
    	String jsonResponse = Utils.convertToJson( trainees );
    	response.getWriter().println( jsonResponse );
    }
    

    /**
     * 
     * @param workoutName
     * @param response
     * @throws IOException
     * /
    @RequestMapping(value = "/secured/getSortedTraineesByGradePerWorkout", method = RequestMethod.GET)
    protected void getSortedTraineesByGrade(
    		@RequestParam String workoutName,
            HttpServletResponse response) throws IOException 
    {
    	Collection<ITrainee> trainees = manager.getSortedTraineesByGradePerWorkout( workoutName );
    	String jsonResponse = Utils.convertToJson( trainees );
    	response.getWriter().println( jsonResponse );
    }*/

    @RequestMapping(value = "/getAllWorkoutsNames", method = RequestMethod.GET)
    protected void getAllWorkoutsNames(
    		HttpServletResponse response) throws Exception
    {
    	Set<String> workouts = manager.getAllWorkoutsNames();
    	String jsonResponse = Utils.convertToJson( workouts );
    	response.getWriter().println( jsonResponse );    	
    }

    @RequestMapping(value = "/getAllWorkoutsMetadata", method = RequestMethod.GET)
    protected void getAllWorkoutsMetadata(
    		HttpServletResponse response) throws Exception
    {
    	Collection<WorkoutMetadata> workouts = manager.getAllWorkoutsMetadata();
    	String jsonResponse = Utils.convertToJson( workouts );
    	response.getWriter().println( jsonResponse );    	
    }

    
    @RequestMapping("/ping")	
	protected void ping(
			HttpServletResponse response) throws Exception{
		log.info( "got to ping" );
		response.getWriter().println("ping response: pong");
	}
	
    @RequestMapping(value = "/getNumberOfRegisteredUsers", method = RequestMethod.GET)
	protected void getNumberOfRegisteredUsers(
			HttpServletResponse response) throws Exception
	{
    	int numberOfRegisteredUsers = manager.getNumberOfRegisteredUsers();
		response.getWriter().println(  numberOfRegisteredUsers );
	}
    
    
    @RequestMapping(value = "/getNumberOfRegisteredResults", method = RequestMethod.GET)
	protected void getNumberOfRegisteredResults(
			HttpServletResponse response) throws Exception
	{
    	int numberOfRegisteredResults = manager.getNumberOfRegisteredResults();
		response.getWriter().println(  numberOfRegisteredResults );
	}

    @RequestMapping("/secured/ping")	
	protected void securedPing(
			HttpServletResponse response) throws Exception{
		log.info( "got to secured ping" );
		response.getWriter().println("secured ping response: pong");
	}


    @RequestMapping(value = "/secured/updateBenchmarkrAccount", method = RequestMethod.POST)
    protected void updateBenchmarkrAccount(
    		@RequestParam("firstName")   String   firstName,
    		@RequestParam("lastName")    String   lastName,
    		@RequestParam("dateOfBirth") String   dateOfBirthText,		//UI format: 1974-10-12
            HttpServletResponse response) throws IOException 
    {
        log.info( "creating Benchmarkr Account");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dateOfBirth;
		try
		{
			dateOfBirth = format.parse(dateOfBirthText);
		} 
		catch (ParseException pe) //if date could not be parsed
		{
            log.error( "date could not be parsed", pe);
            dateOfBirth = new Date();
		}

       	manager.updateBenchmarkrAccount( Utils.getAuthenticatedUsername(),
        			firstName, lastName, dateOfBirth );

        response.setContentType("text/html"); 
		response.setStatus(HttpServletResponse.SC_OK);
   }

    @RequestMapping(value = "/secured/getTraineeById", method = RequestMethod.GET)
    protected void getTraineeById(
            HttpServletResponse response) throws IOException 
    {
    	ITrainee trainee = manager.getTraineeById( Utils.getAuthenticatedUsername() );
    	String jsonResponse = Utils.convertToJson( trainee );
    	response.getWriter().println( jsonResponse );
    }

    
    @RequestMapping(value = "/getStatistics", method = RequestMethod.GET)
    protected void getRegisteredStatistics(
            HttpServletResponse response) throws IOException 
    {
    	PrintWriter writer = response.getWriter();

    	Map<String, List<TimedResult>> stats;
		try
		{
			stats = manager.getRegisteredStatistics();
		} 
        catch (BenchmarkrRuntimeException be)
        {
            log.error( "error: getStatistics failed; ", be);
            writer.println( be.getMessage() );
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    		return;

        }

		String jsonResponse = Utils.convertToJson( stats );
		writer.println( jsonResponse );
    }

}