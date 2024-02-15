package com.petstore.api.tests;

import java.util.HashMap;
import java.util.Map;

import com.petstore.api.baseutils.RestApiUtils;

import io.restassured.response.Response;

public class PetstoreAPIs {
	
	
	public Response createPet(Map<String,Object> createPetPayload) {
		
		 String endPoint = (String) baseTest.dataFromJsonFile.get("createPetEndpoint");
		 
	  return   RestApiUtils.performPost(endPoint, createPetPayload, new HashMap<>());
	  
		
	}

}
