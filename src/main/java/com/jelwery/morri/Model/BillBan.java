package com.jelwery.morri.Model;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id; 
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Document(collection="billBan")
@Data
@NoArgsConstructor
public class BillBan {
    @Id
    private String id;
    private Double totalPrice;
    private BillStatus status;
    private PaymentMethod paymentMethod;
    @DocumentReference
    private Customer customerId;
    private ArrayList<Product> dsSanPhamDaBan;
    @DocumentReference
    private User staff;
    private Double phuThu = 0.0;
    @CreatedDate
    private LocalDateTime createdAt;
    public enum BillStatus
        {
        ON_DELIVERY, CANCELLED, COMPLETED
            
        }
}
