package com.jelwery.morri.DTO;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.BonusPenaltyRecord; 
import com.jelwery.morri.Repository.BonusPenaltyRecordRepository; 

@Component
public class BonusPenaltyRecordDeserializer extends JsonDeserializer<BonusPenaltyRecord> {
    @Autowired
    private BonusPenaltyRecordRepository bonusPenaltyRecordRepository;

    @Override
    public BonusPenaltyRecord deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String bonusPenaltyRecordId = p.getText();
        return bonusPenaltyRecordRepository.findById(bonusPenaltyRecordId)
                .orElseThrow(() -> new ResourceNotFoundException("bonusPenaltyRecord not found with id: " + bonusPenaltyRecordId));
    }
}
