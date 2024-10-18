package com.jelwery.morri.Model;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Document(collection="billBan")
@Data
@NoArgsConstructor
public class BillBan {
    @Id
    private String id;
    private Double totalPrice;
    private PaymentMethod paymentMethod;
    @DBRef
    private Customer customerId;
    private ArrayList<Product> dsSanPhamDaBan;
    @DBRef
    private User staff;
    private Double phuThu = 0.0;
    @CreatedDate
    private LocalDateTime createdAt;
}
