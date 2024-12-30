package com.jelwery.morri.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jelwery.morri.DTO.ProductDeserializer;
import com.jelwery.morri.DTO.SupplierDeserializer;
import com.jelwery.morri.DTO.UserDeserializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor; 

@Document(collection="inventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    @Id
    private String id;
    private String name;
    // total quantity nha
    private int quantity;
    // @DocumentReference
    // @JsonDeserialize(contentUsing = SupplierDeserializer.class)
    // private Supplier supplierId;
    // @DocumentReference
    // @JsonDeserialize(contentUsing = UserDeserializer.class)
    // private User userId;
    // @CreatedDate
    // private LocalDateTime ngayNhapKho;
    // private Double totalPrice;
    // @DocumentReference
    // @JsonDeserialize(contentUsing = ProductDeserializer.class) 
    // private ArrayList<Product> inventoryProducts;
    @DocumentReference(lazy = true)
    @JsonDeserialize(using = SupplierDeserializer.class)
    private Supplier supplier;  
    
    @DocumentReference(lazy = true)
    @JsonDeserialize(using = UserDeserializer.class)

    private User user;     
    
    @CreatedDate
    private LocalDateTime ngayNhapKho;
    private Double totalPrice;
    
    @DocumentReference(lazy = true)
    @JsonDeserialize(using = ProductDeserializer.class)
    private ArrayList<Product> inventoryProducts;
}
