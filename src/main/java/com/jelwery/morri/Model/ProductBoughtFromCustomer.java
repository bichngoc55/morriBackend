package com.jelwery.morri.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;
@Document(collection="productBoughtFromCustomer")
@Data
@NoArgsConstructor 
public class ProductBoughtFromCustomer {
    @Id
    private String productId;
    private Product product; 
    private int quantity; // so luong nhap tu khach
    // private String name;
    // private Double price;
    // private TYPE loaiSanPham;
    // private String material;
}

