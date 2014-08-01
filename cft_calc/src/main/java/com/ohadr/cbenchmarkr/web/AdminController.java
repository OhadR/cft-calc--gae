package com.ohadr.cbenchmarkr.web;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ohadr.auth_flows.interfaces.AuthenticationFlowsProcessor;
import com.ohadr.auth_flows.types.AuthenticationFlowsException;
import com.ohadr.cbenchmarkr.BenchmarkrRuntimeException;
import com.ohadr.cbenchmarkr.Manager;
import com.ohadr.cbenchmarkr.Workout;
import com.ohadr.cbenchmarkr.WorkoutMetadata;
import com.ohadr.cbenchmarkr.utils.Utils;


@Controller
public class AdminController 
{
	private static final String WORKOUT_1 = "workout-1";

	private static Logger log = Logger.getLogger(AdminController.class);

	@Autowired
	private AuthenticationFlowsProcessor flowsProcessor;

	@Autowired
    private Manager manager;


    @RequestMapping(value = "/secured/admin/addWorkout", method = RequestMethod.POST)
    protected void addWorkout(
    		@RequestParam("name")    			String name,
    		@RequestParam("isRepetitionBased")  boolean    isRepetitionBased,
    		@RequestParam("description")    	String description,
            HttpServletResponse response) throws Exception
    {
        log.info( "add workout: " + name);
        
        response.setContentType("text/html"); 

        if( name == null || name.isEmpty() )
        {
            log.error( "workout cannot be null or empty" );
    		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        else
        {
        	WorkoutMetadata workoutMetadata = new WorkoutMetadata(name, description, isRepetitionBased);

        	log.info( workoutMetadata );
           	manager.addWorkout( workoutMetadata );
    		response.setStatus(HttpServletResponse.SC_OK);
        }
    }
    

    @RequestMapping(value = "/setAdmin", method = RequestMethod.PUT)
    protected void setAdmin(
    		HttpServletResponse response) throws Exception
    {
    	response.setContentType("text/html"); 

    	if( manager.setAdmin( Utils.getAuthenticatedUsername() ) )
	    {
    		response.setStatus(HttpServletResponse.SC_OK);
	    }
	    else
	    {
    		response.setStatus( HttpServletResponse.SC_BAD_REQUEST );
	    }
    }

    
	@RequestMapping("/secured/admin/adminPing")	
	protected void adminPing(
			HttpServletResponse response) throws Exception{
		log.info( "got to admin ping" );
		response.getWriter().println("admin pong");
	}
	
	
	/****************************************************************************************************/
	/*************************************************************************************************** /
	  												TESTS
	/****************************************************************************************************/
	@RequestMapping(value = "/secured/admin/test/test", method = RequestMethod.PUT)
    protected void addWorkoutForTrainee(
    		@RequestParam("startIndex")    int startIndex,
    		@RequestParam("amount")    int amount,
            HttpServletResponse response) throws IOException 
    {
		WorkoutMetadata workoutMD = new WorkoutMetadata( WORKOUT_1, "", true );
		manager.addWorkout( workoutMD );
		
		//add 100 users, starting from 'startIndex'
		for(int i = 0; i < amount; ++i)
		{
			int index = i + startIndex;
			String username = index + "@com";
			try
			{
				flowsProcessor.createAccount(
						username,
						"password", "password", 
						username,				//1st name
						String.valueOf( index ), //"lastName", 
						"path");
				
				manager.createBenchmarkrAccount(
						username, 
						true, 
						new Date(System.currentTimeMillis()));
				
				flowsProcessor.setEnabled( username );
			} 
			catch (AuthenticationFlowsException afe)
			{
				log.error( afe.getMessage() );
		
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//				return;
			} 
			catch (BenchmarkrRuntimeException e)
			{
				log.error( e.getMessage() );
				
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
			
	    	Workout workout = new Workout( WORKOUT_1, index );
	    	workout.setDate( new Date(System.currentTimeMillis()) );
	        log.info( "adding workout: " + workout);

	        try
	        {
	        	manager.addWorkoutForTrainee( username, workout );
	        }
	        catch (BenchmarkrRuntimeException be)
	        {
	            log.error( "error adding workout for user", be);
//	            writer.println( be.getMessage() );
	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//	    		return;

	        }
			
		}
/*		
 *cannot do it here, becoz the time of requeest will be to long and exceed the max, so GaE-exception will be raised 		
  		try
		{
			manager.calcAveragesAndGrades();
		} 
		catch (BenchmarkrRuntimeException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
		response.setStatus(HttpServletResponse.SC_CREATED);
    }
	
	
	/**
	 * called for tests
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/secured/admin/test/calcAveragesAndGrades", method = RequestMethod.PUT)
	protected void calcAveragesAndGrades(HttpServletResponse response) throws IOException
	{
		log.info( "calc averages and grades" );

    	try
		{
			manager.calcAveragesAndGrades();
		} 
		catch (BenchmarkrRuntimeException be)
		{
            log.error( "error calcAveragesAndGrades", be);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    		return;
		}

        response.setContentType("text/html"); 
		response.setStatus(HttpServletResponse.SC_OK);
	}
	
	@RequestMapping(value = "/secured/admin/test/resetDB", method = RequestMethod.POST)
	protected void resetDB(HttpServletResponse response) throws IOException
	{
		log.info( "reset DB" );

    	manager.resetDB();

        response.setContentType("text/html"); 
		response.setStatus(HttpServletResponse.SC_OK);
	}
    
}
