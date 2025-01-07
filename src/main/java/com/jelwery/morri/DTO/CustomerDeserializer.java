package com.jelwery.morri.DTO;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.Customer; 
import com.jelwery.morri.Repository.CustomerRepository; 
@JsonComponent
@Component
public class CustomerDeserializer extends JsonDeserializer<Customer> {
    private final CustomerRepository customerRepository;

    public CustomerDeserializer(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String customerId = p.getValueAsString();        
        if (customerId == null || customerId.isEmpty()) return null;
        
        return customerRepository.findById(customerId)
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));
    }
}
