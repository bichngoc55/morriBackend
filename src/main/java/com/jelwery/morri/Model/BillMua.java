package com.jelwery.morri.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id; 
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jelwery.morri.DTO.CustomerDeserializer; 
import com.jelwery.morri.DTO.ProductDeserializer;
import com.jelwery.morri.DTO.UserDeserializer;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection="billMua")
@Data
@NoArgsConstructor
public class BillMua {
    @Id
    private String id;
    private Double totalPrice; 
    @DocumentReference
    @JsonDeserialize(contentUsing = CustomerDeserializer.class) 
    private Customer customerId;
    private String customerName;
    private String SDT;
    private String cccd;
    private Integer status;
    @DocumentReference
    @JsonDeserialize(contentUsing = ProductDeserializer.class)
    private ArrayList<Product> dsSanPhamDaMua;
    @DocumentReference
    @JsonDeserialize(contentUsing = UserDeserializer.class)
    private User staffId;
    @CreatedDate
    private LocalDate createdAt; 
}
