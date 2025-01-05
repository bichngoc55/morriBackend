package com.jelwery.morri.DTO;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.Product;
import com.jelwery.morri.Repository.ProductRepository;

// @Component
// public class ProductSeserializerForOne extends JsonDeserializer<Product> {
//     @Autowired
//     private ProductRepository productRepository;
    
//     @Override
//    public Product deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
//        String userId = p.getText();
//        return productRepository.findById(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("product can not found with id: " + userId));
//    }

// }

@Component
public class ProductSeserializerForOne extends JsonDeserializer<Product> {
    @Autowired
    private ProductRepository productRepository;
    
    @Override
    public Product deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        String productId;
        
        if (node.isObject() && node.has("productId")) {
            productId = node.get("productId").asText();
        } else {
            throw new IllegalArgumentException("Expected object with productId field");
        }
        
        return productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
    }
}