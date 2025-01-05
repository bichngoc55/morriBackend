package com.jelwery.morri.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jelwery.morri.DTO.CustomerDeserializer;
import com.jelwery.morri.DTO.OrderDetailDeserializer;
import com.jelwery.morri.DTO.UserDeserializer;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "billBan")
@Data
@NoArgsConstructor
public class BillBan {
    @Id
    private String id;;
    private Double totalPrice;
    private BillStatus status;
    private PaymentMethod paymentMethod;
    @DocumentReference
    @JsonDeserialize(contentUsing = CustomerDeserializer.class)
    private Customer customer;
    @JsonDeserialize(contentUsing = OrderDetailDeserializer.class)
    private ArrayList<OrderDetail> orderDetails;
    @DocumentReference
    @JsonDeserialize(contentUsing = UserDeserializer.class)
    private User staff;
    private Double additionalCharge = 0.0;
    
    @CreatedDate
    private LocalDateTime createAt;
    private String note;

    public enum BillStatus {
        ON_DELIVERY,
        CANCELLED,
        COMPLETED
    }

   
}