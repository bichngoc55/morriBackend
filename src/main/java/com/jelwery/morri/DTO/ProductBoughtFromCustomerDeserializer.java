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
import com.jelwery.morri.Model.ProductBoughtFromCustomer;
import com.jelwery.morri.Repository.ProductRepository;

@Component
public class ProductBoughtFromCustomerDeserializer extends JsonDeserializer<ProductBoughtFromCustomer> {
    
    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductBoughtFromCustomer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        String productId;
        int quantity = 1;

        if (node.isObject() && node.has("productId")) {
            productId = node.get("productId").asText();
        } else {
            throw new IllegalArgumentException("Expected object with productId field");
        }

        if (node.has("quantity")) {
            quantity = node.get("quantity").asInt(); // Ensure quantity is properly extracted
        }

        // Use ProductRepository to find the Product entity
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        // Create a new ProductBoughtFromCustomer and map the fields
        ProductBoughtFromCustomer productBoughtFromCustomer = new ProductBoughtFromCustomer();
        productBoughtFromCustomer.setProduct(product);
        productBoughtFromCustomer.setProductId(productId);
        productBoughtFromCustomer.setQuantity(quantity);  // Make sure to map all necessary fields
        return productBoughtFromCustomer;
    }
}

