package com.jelwery.morri.DTO;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.stereotype.Component;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired; 

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.jelwery.morri.Exception.ResourceNotFoundException; 
import com.jelwery.morri.Model.ProductBoughtFromCustomer; 
import com.jelwery.morri.Repository.ProductBoughtFromCustomerRepository; 

@Component
public class ProductBoughtFromCustomerDeserializer extends JsonDeserializer<ProductBoughtFromCustomer>{
    @Autowired
   private ProductBoughtFromCustomerRepository customerRepository;

   @Override
   public ProductBoughtFromCustomer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
       String userId = p.getText();
       return customerRepository.findById(userId)
               .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
   }

}
