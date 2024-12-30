package com.jelwery.morri.Model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
@Document(collection = "absence")

@Data
public class Absence {
    @Id
    private String id;
    
    @CreatedDate
    private LocalDateTime date;
    private String employeeId; 
    private String reason;
    private AbsenceStatus status; 
    
    public enum AbsenceStatus {
        PENDING,
        APPROVED,
        REJECTED
    }

}
