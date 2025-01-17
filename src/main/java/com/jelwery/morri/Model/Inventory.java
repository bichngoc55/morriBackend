package com.jelwery.morri.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jelwery.morri.DTO.InventoryProductDeserializer;
import com.jelwery.morri.DTO.ProductSeserializerForOne;
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
    private int quantity;

    @DocumentReference(lazy = true)
    @JsonDeserialize(using = SupplierDeserializer.class)
    private Supplier supplier;  

    @DocumentReference(lazy = true)
    @JsonDeserialize(using = UserDeserializer.class)
    private User user;     

    @CreatedDate
    private LocalDateTime ngayNhapKho;
    private Double totalPrice;
    @DocumentReference
    @JsonDeserialize(using = InventoryProductDeserializer.class)
    private ArrayList<InventoryProduct> inventoryProducts;
}
