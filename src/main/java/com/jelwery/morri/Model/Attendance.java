package com.jelwery.morri.Model;
 
import java.util.List;

import org.springframework.data.annotation.Id; 
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jelwery.morri.DTO.AbsenceDeserializer;
import com.jelwery.morri.DTO.AttendanceRecordDeserializer;
import com.jelwery.morri.DTO.UserDeserializer;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection = "attendance")
@NoArgsConstructor
public class Attendance {
    @Id
    private String id;
    
    @DocumentReference
    @JsonDeserialize(contentUsing = UserDeserializer.class)
    private User employeeId; 
    private int year;
    private int month;
    @DocumentReference
    @JsonDeserialize(contentUsing = AttendanceRecordDeserializer.class)
    private List<AttendanceRecord> attendanceRecords;
    private Integer workingDays;
    @DocumentReference
    @JsonDeserialize(contentUsing = AbsenceDeserializer.class)
    private List<Absence> absences;
     
    private Double totalWorkingHours;
    private Integer totalAbsences;
    private Integer totalLateArrivals;

}
 
