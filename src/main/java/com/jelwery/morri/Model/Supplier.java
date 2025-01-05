package com.jelwery.morri.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor; 
 
@Document(collection="supplier")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {
    @Id
    private String id;
    // @Indexed(unique = true)
    private String supplierName;
    private String supplierAddress;
    private String supplierPhone;
     // tui note o day li do k cho them list inventory vao vi ca 2 th deu tro vao nhau
}
