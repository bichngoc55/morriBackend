package com.jelwery.morri.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jelwery.morri.DTO.ProductBoughtFromCustomerDeserializer;
import com.jelwery.morri.DTO.ProductDeserializer;
import com.jelwery.morri.DTO.ProductSeserializerForOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document("cart_item")
public class CartItem {
    @Id
    private String id;
     @DBRef 
    private Product product;

    private Integer selectedQuantity;

}
