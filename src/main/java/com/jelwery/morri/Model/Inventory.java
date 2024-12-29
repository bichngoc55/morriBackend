package com.jelwery.morri.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id; 
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jelwery.morri.DTO.SupplierDeserializer;
import com.jelwery.morri.DTO.UserDeserializer;

import java.time.LocalDateTime;
import java.util.ArrayList; 

@Document(collection="inventory")
@Data
@NoArgsConstructor
public class Inventory {
    @Id
    private String id;
    private String name;
    // total quantity nha
    private int quantity;
    @DocumentReference
    @JsonDeserialize(contentUsing = SupplierDeserializer.class)
    private Supplier supplierId;
    @DocumentReference
    @JsonDeserialize(contentUsing = UserDeserializer.class)
    private User userId;
    @CreatedDate
    private LocalDateTime ngayNhapKho;
    private Double totalPrice;
    private ArrayList<Product> inventoryProducts;

}
