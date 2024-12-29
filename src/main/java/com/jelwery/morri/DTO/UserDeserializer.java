package com.jelwery.morri.DTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.User;
import com.jelwery.morri.Repository.UserRepository;
import com.fasterxml.jackson.core.JsonParser; 

import java.io.IOException;
@Component
public class UserDeserializer extends JsonDeserializer<User>{
     @Autowired
    private UserRepository userRepository;

    @Override
    public User deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String userId = p.getText();
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

}
