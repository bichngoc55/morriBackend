package com.jelwery.morri.Model;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection="schedule")
@Data
@NoArgsConstructor
public class Schedule {
    @Id
     private String id;
     
    private String employeeId;
    private LocalDateTime workDate;
    private LocalTime startTime =LocalTime.of(7, 0);;
    private LocalTime endTime=LocalTime.of(17, 0); ; 
     @Transient
    private User employee;

}
