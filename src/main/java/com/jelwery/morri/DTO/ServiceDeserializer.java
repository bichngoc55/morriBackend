package com.jelwery.morri.DTO;

import org.springframework.beans.factory.annotation.Autowired; 

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.Service; 
import com.jelwery.morri.Repository.ServiceRepository; 
import com.fasterxml.jackson.core.JsonParser; 

import java.io.IOException;
public class ServiceDeserializer extends JsonDeserializer<Service>{
    @Autowired
   private ServiceRepository serviceRepository;

   @Override
   public Service deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
       String serviceId = p.getText();
       return serviceRepository.findById(serviceId)
               .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + serviceId));
   }

}