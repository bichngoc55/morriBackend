package com.jelwery.morri.Model;

import java.lang.ref.Reference;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jelwery.morri.DTO.UserDeserializer;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection="schedule")
@Data
@NoArgsConstructor
public class Schedule { 
    @Id
    private String id;
    
    private LocalDateTime workDate;
    
    @DocumentReference
    @JsonDeserialize(contentUsing = UserDeserializer.class)
    private List<User> morningShifts;
    
    @DocumentReference
    @JsonDeserialize(contentUsing = UserDeserializer.class)
    private List<User> afternoonShifts;
    
    private LocalTime startTime = LocalTime.of(7, 0);
    private LocalTime endTime = LocalTime.of(17, 0);
    
    private String createdBy;  
    
    private ScheduleStatus status = ScheduleStatus.ACTIVE;
    
    public enum ScheduleStatus {
        ACTIVE,
        CANCELLED,
        COMPLETED
    }

}
