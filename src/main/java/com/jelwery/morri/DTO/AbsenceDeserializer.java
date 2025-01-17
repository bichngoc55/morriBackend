package com.jelwery.morri.DTO;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.Absence;
import com.jelwery.morri.Model.User;
import com.jelwery.morri.Model.Absence.AbsenceStatus;
import com.jelwery.morri.Repository.AbsenceRepository;
import com.jelwery.morri.Repository.UserRepository;
import com.fasterxml.jackson.core.JsonParser; 

@Component
public class AbsenceDeserializer extends JsonDeserializer<Object> {
     @Autowired
    private UserRepository userRepository;
    @Autowired
    private AbsenceRepository absenceRepository;

    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
         
        if (node.isArray()) {
            List<Absence> absences = new ArrayList<>();
            for (JsonNode absenceNode : node) {
                absences.add(deserializeSingle(absenceNode));
            }
            return absences;
        } else {
            return deserializeSingle(node);
        }
    }

    private Absence deserializeSingle(JsonNode absenceNode) {
        String id = absenceNode.has("id") ? absenceNode.get("id").asText() : null;
        Absence absence = id != null ? absenceRepository.findById(id).orElse(new Absence()) : new Absence();

        if (absenceNode.has("date")) {
            absence.setDate(LocalDateTime.parse(absenceNode.get("date").asText()));
        }
        if (absenceNode.has("reason")) {
            absence.setReason(absenceNode.get("reason").asText());
        }
        if (absenceNode.has("status")) {
            absence.setStatus(AbsenceStatus.valueOf(absenceNode.get("status").asText()));
        }
        if (absenceNode.has("employee") && absenceNode.get("employee").has("id")) {
            User employee = userRepository.findById(absenceNode.get("employee").get("id").asText())
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " ));
            absence.setEmployee(employee);
        }

        return absence;
    }
}