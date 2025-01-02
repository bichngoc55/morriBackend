package com.jelwery.morri.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Document(collection="productBoughtFromCustomer")
@Data
@NoArgsConstructor 
public class ProductBoughtFromCustomer {
    @Id
    private String id; 
    private String name; 
    private Double price;
    private int quantity; 
    private TYPE loaiSanPham;
    private String material;





    
}
