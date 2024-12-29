package com.jelwery.morri.Model;
 
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection = "attendance")
@NoArgsConstructor
public class Attendance {
     @Id
    private String id;
    private String employeeId;
    private int year;
    private int month;
    private List<AttendanceRecord> attendanceRecords;
    private Integer workingDays;
    @Transient
    private User employee;

}
 
