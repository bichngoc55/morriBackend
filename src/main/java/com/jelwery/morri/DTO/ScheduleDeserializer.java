package com.jelwery.morri.DTO;
 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
 
import com.jelwery.morri.Model.Schedule; 
import com.jelwery.morri.Repository.ScheduleRepository; 

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.jelwery.morri.Exception.ResourceNotFoundException; 
import com.fasterxml.jackson.core.JsonParser; 

import java.io.IOException;
@Component
public class ScheduleDeserializer extends JsonDeserializer<Schedule>{
    @Autowired
   private ScheduleRepository userRepository;

   @Override
   public Schedule deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
       String userId = p.getText();
       return userRepository.findById(userId)
               .orElseThrow(() -> new ResourceNotFoundException("Schdule not found with id: " + userId));
   }

}
