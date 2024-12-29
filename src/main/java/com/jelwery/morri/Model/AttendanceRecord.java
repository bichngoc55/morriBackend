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
    private String status;
    private Double workingHours;
 }
