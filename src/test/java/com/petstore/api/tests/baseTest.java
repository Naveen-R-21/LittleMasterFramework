package com.petstore.api.tests;

import java.io.IOException;
import java.util.Map;

import com.petstore.api.jsonutils.JsonUtils;

public class baseTest {
	
	public static Map <String, Object> dataFromJsonFile;
	
	
	static {
		
		try {
			dataFromJsonFile = JsonUtils.getJsonData("/src/test/resources/petstoreData.json");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}

}
