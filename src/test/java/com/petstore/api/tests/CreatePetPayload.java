package com.petstore.api.tests;

import java.util.HashMap;
import java.util.Map;

public class CreatePetPayload {
    
    public static Map<String, Object> getCreatePetPayload(String id, String name, String categoryName, String status, String[] photoURLs) {
        
        Map<String, Object> payload = new HashMap<>();
        
        payload.put("id", id );
        payload.put("name", name);
        payload.put("category", Map.of("id", 0, "name", categoryName));
        payload.put("status", status);
        payload.put("photoUrls", photoURLs);
    
        return payload;
        
        
    }

}

