package com.jelwery.morri.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;
@Document(collection="product")
@Data
@NoArgsConstructor
public class Product {
    @Id
    private String id;
    @Indexed(unique=true)
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private TYPE loaiSanPham;
    private int quantity;
    private double weight;
    private STATUS status;
    private String chiPhiPhatSinh;
    // li do tui k code inventoryId o day vi 2 th tro den nhau
}
