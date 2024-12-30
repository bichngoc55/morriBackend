package com.jelwery.morri.DTO;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired; 

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.Product; 
import com.jelwery.morri.Repository.ProductRepository; 
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.util.ArrayList;


@Component
public class ProductDeserializer  extends JsonDeserializer<ArrayList<Product>> {
    @Autowired
   private ProductRepository productRepository;
 

//    @Override
//    public Product deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
//        String productId = p.getText();
//        return productRepository.findById(productId)
//            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
//    }
 @Override
    public ArrayList<Product> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ArrayList<Product> products = new ArrayList<>();
        p.setCodec(new ObjectMapper());
        
        if (p.isExpectedStartArrayToken()) {
            while (p.nextToken() != JsonToken.END_ARRAY) {
                if (p.hasToken(JsonToken.VALUE_STRING)) {
                    String productId = p.getText();
                    Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
                    products.add(product);
                }
            }
        }
        
        return products;
    }
}

