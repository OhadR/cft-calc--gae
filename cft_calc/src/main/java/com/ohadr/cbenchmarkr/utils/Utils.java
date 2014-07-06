package com.ohadr.cbenchmarkr.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

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
}
