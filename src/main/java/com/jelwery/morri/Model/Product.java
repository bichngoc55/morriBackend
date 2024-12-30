package com.jelwery.morri.Model;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document; 

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties; 

import lombok.Data;
import lombok.NoArgsConstructor; 
 
@JsonAutoDetect
 @JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection="product")
@Data
@NoArgsConstructor
public class Product {
    @Id
    private String id;
    @Indexed(unique=true)
    private String name;
    @Indexed(unique=true)
    private String code;
    private String description;
    private String material;
    private Double costPrice; 
    private Double sellingPrice;
    private List<String> imageUrl;
    private TYPE loaiSanPham;
    private int quantity;
    private double weight;
    private STATUS status;
    private String chiPhiPhatSinh;
    @Reference
    @DBRef
    private Supplier supplierId;
    @CreatedDate
    private LocalDateTime entryDate;
    // li do tui k code inventoryId o day vi 2 th tro den nhau
}
