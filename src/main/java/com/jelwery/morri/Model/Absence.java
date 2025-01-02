package com.jelwery.morri.Model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jelwery.morri.DTO.UserDeserializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Document(collection = "absence")
@NoArgsConstructor
@AllArgsConstructor

@Data
public class Absence {
    @Id
    private String id;
    @CreatedDate
    private LocalDateTime date;
    
    private String reason;
    private AbsenceStatus status; 
    @DocumentReference
    @JsonDeserialize(contentUsing = UserDeserializer.class)
    private User employeeId; 
    public enum AbsenceStatus {
        PENDING,
        APPROVED,
        REJECTED
    }

}
