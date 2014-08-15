package com.ohadr.cbenchmarkr.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class Utils
{
	private static Logger log = Logger.getLogger(Utils.class);

    /**
     * 
     * @param objectToConvert
     * @return: JSON-String that represents objectToConvert. null if objectToConvert is null.
     */
	public static <T> String convertToJson(T objectToConvert)
    {
        if( objectToConvert == null )
        	return null;
    	
    	ObjectMapper mapper = new ObjectMapper();
        ByteArrayOutputStream bis = new ByteArrayOutputStream();

        String json = null;
        try
        {
            mapper.writeValue(bis, objectToConvert);
            json = bis.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("error converting " + objectToConvert.getClass().getSimpleName() + " to JSON");
        }

        log.debug("convertToJson() result: " + json);
        return json;
    }


    public static <T> T convertFromJson(String json, Class<T> typeParameterClass)
    {
        ObjectMapper mapper = new ObjectMapper();
        
        T t = null;
		try
		{
			t = (T) mapper.readValue(json, typeParameterClass);
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
            log.error("error converting " + json + " from JSON");
		}
        return t;
    }
    
    
	public static String getAuthenticatedUsername()
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName(); //get logged in username
		log.info( "logged in user: " + name );
		return name;
	}
	
	/**
	 * 
	 * @param input the input double
	 * @param numDigitsAfterDecimalPoint num Digits After Decimal Point. e.g. if it is 3, and input is 4.123456789, 
	 * the output will be 4.123
	 * @return
	 */
	public static double formatDouble(double input, int numDigitsAfterDecimalPoint) 
	{
		double factor = Math.pow(10, numDigitsAfterDecimalPoint);
		int nResult = (int)(input * factor);
		double output = nResult / factor;
		return output;
	}
	
    
}
