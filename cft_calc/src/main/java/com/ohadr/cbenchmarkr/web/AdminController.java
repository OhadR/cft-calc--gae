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
public class AdminController 
{
	private static Logger log = Logger.getLogger(AdminController.class);

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
    
    
	@RequestMapping("/secured/admin/adminPing")	
	protected void adminPing(
			HttpServletResponse response) throws Exception{
		log.info( "got to admin ping" );
		response.getWriter().println("admin pong");
	}
    
}
