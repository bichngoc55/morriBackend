package com.jelwery.morri.Model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Absence {
    @Id
    private String id;
     @CreatedDate
        private LocalDateTime date;    
        private String reason;

}
