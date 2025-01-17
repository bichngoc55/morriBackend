package com.jelwery.morri.Model;
 
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize; 
import com.jelwery.morri.DTO.ProductSeserializerForOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
    @Id
    private String ID;
     @DocumentReference
         @JsonDeserialize(contentUsing = ProductSeserializerForOne.class)
        private Product product;
        private int quantity;
        private double unitPrice;
        private double subtotal;
}
