package com.jelwery.morri.DTO;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.Customer; 
import com.jelwery.morri.Repository.CustomerRepository; 

@JsonComponent
public class CustomerDeserializer extends JsonDeserializer<Customer>{
     @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String userId = p.getText();
        return customerRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

}

