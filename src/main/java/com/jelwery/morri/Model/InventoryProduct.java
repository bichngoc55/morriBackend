package com.jelwery.morri.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jelwery.morri.DTO.ProductSeserializerForOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Document(collection = "inventory_product")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryProduct {
        @Id
        private String id;
   @DocumentReference(lazy = true)
        @JsonDeserialize(using = ProductSeserializerForOne.class)  
        private Product product;
        private int enteredQuantity;
}
