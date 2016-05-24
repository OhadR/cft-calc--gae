package com.ohadr.cbenchmarkr.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ohadr.cbenchmarkr.BenchmarkrRuntimeException;
import com.ohadr.cbenchmarkr.Manager;
import com.ohadr.cbenchmarkr.WorkoutMetadata;
import com.ohadr.cbenchmarkr.utils.Utils;


@Controller
public class AdminController 
{
	private static Logger log = Logger.getLogger(AdminController.class);

	@Autowired
    private Manager manager;


    @RequestMapping(value = "/secured/admin/addWorkout", method = RequestMethod.POST)
    protected void addWorkout(
    		@RequestParam("name")    			String name,
    		@RequestParam("isRepetitionBased")  boolean    isRepetitionBased,
    		@RequestParam("units")  			String units,
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
        	WorkoutMetadata workoutMetadata = new WorkoutMetadata(name, description, isRepetitionBased, units);

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
	
	/**
	 * called by a cron job as GET. URL starts with 'cron' so only admin can invoke the call (web.xml)
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/cron/calcAveragesAndGrades")
	protected void calcAveragesAndGrades(HttpServletResponse response) throws IOException
	{
		log.debug( "calc averages and grades" );

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
	
	
	@RequestMapping("/cron/recordStatistics")
	protected void recordStatistics( HttpServletResponse response ) throws IOException
	{
		manager.recordStatistics();

        response.setContentType("text/html"); 
		response.setStatus(HttpServletResponse.SC_OK);
	}

    @RequestMapping(value = "/secured/admin/clearCache", method = RequestMethod.POST)
    protected void clearCache( HttpServletResponse response ) throws Exception
    {
        log.info( "clearCache" );
        
        response.setContentType("text/html"); 

       	manager.clearCache();
		response.setStatus(HttpServletResponse.SC_OK);
    }
}
