package com.jelwery.morri.Model;

import java.time.LocalDateTime;

import org.joda.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document("voucher")
@NoArgsConstructor
public class Voucher {
@Id
    private String id;
    private String voucherName; 
    private String voucherCode; 
    private Double discount; 
    private String description; 
    private String status; 
    private LocalDateTime startDate; 
    private LocalDateTime endDate;
}
