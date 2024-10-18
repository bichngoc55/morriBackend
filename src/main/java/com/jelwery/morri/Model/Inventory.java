package com.jelwery.morri.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

@Document(collection="inventory")
@Data
@NoArgsConstructor
public class Inventory {
    @Id
    private String id;
    private String name;
    private int quantity;
    @DBRef
    private Supplier supplierId;
    @DBRef
    private User userId;
    @CreatedDate
    private LocalDateTime ngayNhapKho;
    private Double totalPrice;
    private ArrayList<Product> inventoryProducts;

}
