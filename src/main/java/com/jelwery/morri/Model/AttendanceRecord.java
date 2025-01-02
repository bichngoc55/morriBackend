package com.jelwery.morri.Model;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
