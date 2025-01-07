package com.jelwery.morri.DTO;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.User;
import com.jelwery.morri.Repository.UserRepository;
@JsonComponent
public class UserDeserializer extends JsonDeserializer<User>{
     @Autowired
    private UserRepository userRepository;

    @Override
    public User deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String userId = p.getText();
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User can not found with id: " + userId));
    }

}
