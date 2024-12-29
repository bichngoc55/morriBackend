package com.jelwery.morri.Model;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AttendanceRecord {
    private LocalDateTime date;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private AttendanceStatus status;
    private Double workingHours;
    private String notes;
    
    public enum AttendanceStatus {
        PRESENT,
        ABSENT,
        LATE,
        LEAVE,
        PENDING
    }
 }
