package com.jelwery.morri.Model;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jelwery.morri.DTO.UserDeserializer;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "attendanceRecord")
@Data
@NoArgsConstructor
public class AttendanceRecord {
    @Id
    private String id;
    private LocalDateTime date;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private AttendanceStatus status;
    private Double workingHours;
    private String notes;
    private CHECKTYPE checkType;
    @DocumentReference
    @JsonDeserialize(using = UserDeserializer.class)
    private User employee; 
    public enum CHECKTYPE {
        IN,
        OUT
    }
    
    
    public enum AttendanceStatus {
        PRESENT,
        EARLY,
        ABSENT,
        LATE,
        LEAVE,
        PENDING
    }
 }
