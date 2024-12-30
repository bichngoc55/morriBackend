package com.jelwery.morri.DTO;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.Product;
import com.jelwery.morri.Repository.ProductRepository;


@Component
public class ProductSeserializerForOne extends JsonDeserializer<Product> {
    @Autowired
    private ProductRepository productRepository;
     @Override
   public Product deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
       String productId = p.getText();
       return productRepository.findById(productId)
           .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
   }
}
