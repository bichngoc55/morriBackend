package com.jelwery.morri.Model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import lombok.Data;

@Data
public class Absence {
     @CreatedDate
        private LocalDateTime date;    
        private String reason;

}
