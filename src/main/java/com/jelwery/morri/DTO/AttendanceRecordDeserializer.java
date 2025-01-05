package com.jelwery.morri.DTO;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jelwery.morri.Repository.AttendanceRecordRepository;
import com.jelwery.morri.Repository.UserRepository;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jelwery.morri.Exception.ResourceNotFoundException; 
import com.jelwery.morri.Model.AttendanceRecord;
import com.jelwery.morri.Model.User;
import com.fasterxml.jackson.core.JsonParser; 

@Component
public class AttendanceRecordDeserializer   extends JsonDeserializer<Object>{
    @Autowired
    private AttendanceRecordRepository attendanceRecordRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        
        // Handle both single object and array cases
        if (node.isArray()) {
            List<AttendanceRecord> records = new ArrayList<>();
            for (JsonNode recordNode : node) {
                records.add(deserializeSingle(recordNode));
            }
            return records;
        } else {
            return deserializeSingle(node);
        }
    }

    private AttendanceRecord deserializeSingle(JsonNode recordNode) {
        String id = recordNode.has("id") ? recordNode.get("id").asText() : null;
        AttendanceRecord record = id != null ? attendanceRecordRepository.findById(id).orElse(new AttendanceRecord()) : new AttendanceRecord();

        if (recordNode.has("date")) {
            record.setDate(LocalDateTime.parse(recordNode.get("date").asText()));
        }
        if (recordNode.has("checkIn")) {
            record.setCheckIn(LocalDateTime.parse(recordNode.get("checkIn").asText()));
        }
        if (recordNode.has("checkOut")) {
            record.setCheckOut(LocalDateTime.parse(recordNode.get("checkOut").asText()));
        }
        if (recordNode.has("status")) {
            record.setStatus(AttendanceRecord.AttendanceStatus.valueOf(recordNode.get("status").asText()));
        }
        if (recordNode.has("workingHours")) {
            record.setWorkingHours(recordNode.get("workingHours").asDouble());
        }
        if (recordNode.has("notes")) {
            record.setNotes(recordNode.get("notes").asText());
        }
        if (recordNode.has("checkType")) {
            record.setCheckType(AttendanceRecord.CHECKTYPE.valueOf(recordNode.get("checkType").asText()));
        }
        if (recordNode.has("employee") && recordNode.get("employee").has("id")) {
            User employee = userRepository.findById(recordNode.get("employee").get("id").asText())
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " ));
            record.setEmployee(employee);
        }

        return record;
    }

}