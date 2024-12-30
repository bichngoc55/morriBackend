package com.jelwery.morri.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jelwery.morri.DTO.SupplierDeserializer;
import com.jelwery.morri.DTO.UserDeserializer;

import lombok.Data;
import lombok.NoArgsConstructor; 

@Document(collection="inventory")
@Data
@NoArgsConstructor
public class Inventory {
    @Id
    private String id;
    @DBRef
    @JsonDeserialize(contentUsing = SupplierDeserializer.class)
    private Supplier supplierId;
    @DBRef
    @JsonDeserialize(contentUsing = UserDeserializer.class)
    private User userId;
    @CreatedDate
    private LocalDateTime ngayNhapKho;
    private Double totalPrice;
    private String note;

    @DBRef
    private ArrayList<Product> inventoryProducts;
}
