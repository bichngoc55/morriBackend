package com.jelwery.morri.Model;

import java.sql.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jelwery.morri.DTO.CartItemDeserializer;
import com.jelwery.morri.DTO.CustomerDeserializer;
import com.jelwery.morri.DTO.UserDeserializer;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection = "cart")  
public class Cart {
     @Id
    private String id;

    @DBRef
    @JsonDeserialize(contentUsing = CustomerDeserializer.class)
    private Customer user;
    @DBRef
    @JsonDeserialize(contentUsing = CartItemDeserializer.class)
    private List<CartItem> items;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;
}
