package com.jelwery.morri.DTO;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.Customer; 
import com.jelwery.morri.Repository.CustomerRepository; 

@Component
public class CustomerDeserializer extends JsonDeserializer<Customer>{
     @Autowired
    private CustomerRepository customerRepository;

     
    @Override
    public Customer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        // Get the customer ID from JSON
        String customerId = p.getValueAsString();
        
        if (customerId == null || customerId.isEmpty()) {
            return null;
        }
        
        try {
            // Attempt to find the customer by ID
            return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));
        } catch (Exception e) {
            throw new IOException("Error deserializing Customer: " + e.getMessage(), e);
        }
    }
}

