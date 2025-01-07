package com.jelwery.morri.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import lombok.Data;
import lombok.NoArgsConstructor;
@Document(collection="productBoughtFromCustomer")
@Data
@NoArgsConstructor 
public class ProductBoughtFromCustomer {
    @Id
    private String id;
    @DocumentReference
    private Product productId; 
    private int quantity;  
}

