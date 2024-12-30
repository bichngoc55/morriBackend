package com.jelwery.morri.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

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
    @DBRef
    @Reference
    private Customer customer;
    private ArrayList<OrderDetail> orderDetails;
    @DBRef
    @Reference
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

    @Data
    @NoArgsConstructor
    public static class OrderDetail {
       
        @DBRef
        @Reference
        private Product product;
        private int quantity;
        private double unitPrice;
        private double subtotal;

        public OrderDetail(Product product, int quantity, double unitPrice, double subtotal) {
            this.product = product;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
            this.subtotal = subtotal;
        }
    }
}