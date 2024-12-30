package com.jelwery.morri.DTO;
import java.io.IOException; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jelwery.morri.Repository.AttendanceRecordRepository; 
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.jelwery.morri.Exception.ResourceNotFoundException; 
import com.jelwery.morri.Model.AttendanceRecord;
import com.fasterxml.jackson.core.JsonParser; 

@Component
public class AttendanceRecordDeserializer  extends JsonDeserializer<AttendanceRecord> {
    @Autowired
   private AttendanceRecordRepository attendanceRecordRepository;

   @Override
   public AttendanceRecord deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
       String attendanceRecordId = p.getText();
       return attendanceRecordRepository.findById(attendanceRecordId)
               .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found with id: " + attendanceRecordId));
   }

}