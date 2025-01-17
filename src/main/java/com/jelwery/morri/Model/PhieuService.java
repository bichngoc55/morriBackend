package com.jelwery.morri.Model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed; 
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jelwery.morri.DTO.CustomerDeserializer;
import com.jelwery.morri.DTO.ServiceDeserializer;
import com.jelwery.morri.DTO.UserDeserializer;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection="phieuService")
@NoArgsConstructor
@Data
public class PhieuService {
    @Id
    private String id; 
    private String nameService;
    private String customerName;
    private String customerPhone;
    private String customerGender;
    @DocumentReference
    @JsonDeserialize(contentUsing = CustomerDeserializer.class)
    private Customer customerID;
    @DocumentReference
     @JsonDeserialize(contentUsing = ServiceDeserializer.class)
    private List<Service> services;
    private String description; 
     @DocumentReference
    @JsonDeserialize(contentUsing = UserDeserializer.class)
    private User staffLapHoaDon;    
    @DocumentReference
    @JsonDeserialize(contentUsing = UserDeserializer.class)
    private User staffLamDichVu;  
    private int quantity;
    private Double totalPrice;
    @CreatedDate
    private LocalDateTime createdAt; 
    private STATUS phieuServiceStatus;
      
    public enum STATUS{
        COMPLETED,
        NOT_YET
    }
}
