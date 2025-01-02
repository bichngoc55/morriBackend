package com.jelwery.morri.Model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jelwery.morri.DTO.UserDeserializer;

import lombok.Data;
@Document(collection = "absence")

@Data
public class Absence {
    @Id
    private String id;
    
    @CreatedDate
    private LocalDateTime date;
    @DocumentReference
    @JsonDeserialize(contentUsing = UserDeserializer.class)
    private User employeeId; 
    private String reason;
    private AbsenceStatus status; 
    
    public enum AbsenceStatus {
        PENDING,
        APPROVED,
        REJECTED
    }

}
