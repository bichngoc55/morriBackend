package com.jelwery.morri.DTO;
import java.io.IOException; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.Absence;
import com.jelwery.morri.Repository.AbsenceRepository;
import com.fasterxml.jackson.core.JsonParser; 

@Component
public class AbsenceDeserializer extends JsonDeserializer<Absence>{
    @Autowired
   private AbsenceRepository absenceRepository;

   @Override
   public Absence deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
       String userId = p.getText();
       return absenceRepository.findById(userId)
               .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
   }

}