package com.ohadr.cbenchmarkr.core;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.ohadr.auth_flows.interfaces.AuthenticationFlowsProcessor;
import com.ohadr.auth_flows.types.FlowsConstatns;
import com.ohadr.cbenchmarkr.BenchmarkrRuntimeException;
import com.ohadr.cbenchmarkr.Manager;
import com.ohadr.crypto.service.CryptoService;

/**
 * replaces the default one, that extends 'SavedRequestAwareAuthenticationSuccessHandler', which redirects the call.
 * we implement REST so we do not want to redirect.
 * @author OhadR
 *
 */
@Service("RESTauthenticationSuccessHandler")
public class AuthenticationSuccessHandler extends MySavedRequestAwareAuthenticationSuccessHandler
{
	@Autowired
	private AuthenticationFlowsProcessor processor;

	@Autowired
	private CryptoService cryptoService;
	
    @Autowired
    private Manager manager;
	

	private static Logger log = Logger.getLogger(AuthenticationSuccessHandler.class);

	public AuthenticationSuccessHandler()
	{
	}
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException
    {
	    String username = authentication.getName();
	    log.info("login success for user: " + username);

	    boolean passChangeRequired = processor.setLoginSuccessForUser(username);
		if(passChangeRequired)
		{
			//start "change password" flow:
			log.info("password expired for user " + username);
			String encUser = cryptoService.generateEncodedString(username);
			//redirect to a set new password page:
			response.sendRedirect( FlowsConstatns.LOGIN_FORMS_DIR + "/changePassword.jsp?username=" + username + 
					"&" + FlowsConstatns.HASH_PARAM_NAME + "=" + encUser);
			
			return;

		}

		try
		{
			postLogin( username );
		} 
		catch (BenchmarkrRuntimeException e)
		{
			log.error("user Login Success failed for " + username + "; " + e.getMessage());
		}
		
		/////////////////////////////////////////
		// changeSessionTimeout(request);
		/////////////////////////////////////////

		
    	super.onAuthenticationSuccess(request, response, authentication);
    }
    
    private void changeSessionTimeout(HttpServletRequest request)
    {
		HttpSession session = request.getSession(false);
    	
    	int interval = session.getMaxInactiveInterval();
    	System.out.println("session interval is " + interval);
    	session.setMaxInactiveInterval(50);
    	
    }
    
	/**
	 * we would like to save in the DB the time of the login, so we know how long the 
	 * user has not login to the app. in addition, we same metrics:
	 * @throws BenchmarkrRuntimeException - if username was not found in repository. 
	 */
	private void postLogin( String username ) throws BenchmarkrRuntimeException
	{
		manager.userLoginSuccess( username );
		
	}
    

}
