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
}
