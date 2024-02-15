package com.petstore.api.jsonutils;

import java.util.Map;
import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	
	public static ObjectMapper objectMapper = new ObjectMapper();
	
	
	public static Map<String, Object> getJsonData(String jsonFilePath) throws IOException{
	    String filePath = System.getProperty("user.dir") + jsonFilePath;
	    
	    Map<String, Object> jsonData = objectMapper.readValue(new File(filePath), new TypeReference<>() {});
		return jsonData;
		
	}
}

