package com.petstore.api.tests;

import java.io.IOException;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.response.Response;

public class PetsTestcases extends PetstoreAPIs {
    
    @Test
    public void addPet() throws IOException {
    	
    
        Map<String, Object> payload = CreatePetPayload.getCreatePetPayload("0", "Black home", "Dove black", "available", new String[]{"string"});
        
        Response response = createPet(payload);
        
        Assert.assertEquals(response.statusCode(), 200, "Status code is not 200");
       // Assert.assertEquals(response.jsonPath().getString("name"), "Dove black", "Name does not match. Actual name: " + response.jsonPath().getString("name"));
    }

}

