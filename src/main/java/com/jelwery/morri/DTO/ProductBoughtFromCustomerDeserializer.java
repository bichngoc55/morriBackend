package com.jelwery.morri.DTO;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.Product;
import com.jelwery.morri.Model.ProductBoughtFromCustomer;
import com.jelwery.morri.Repository.ProductBoughtFromCustomerRepository; 

@JsonComponent
public class ProductBoughtFromCustomerDeserializer extends JsonDeserializer<ProductBoughtFromCustomer> {
    
    @Override
    public ProductBoughtFromCustomer deserialize(JsonParser p, DeserializationContext ctxt) 
            throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        ProductBoughtFromCustomer productBought = new ProductBoughtFromCustomer();

        // Handle both string ID and object formats
        if (node.has("productId")) {
            JsonNode productIdNode = node.get("productId");
            Product product = new Product();
            
            if (productIdNode.isObject()) {
                product.setId(productIdNode.get("id").asText());
            } else {
                product.setId(productIdNode.asText());
            }
            
            productBought.setProductId(product);
        }

        if (node.has("quantity")) {
            productBought.setQuantity(node.get("quantity").asInt());
        }

        return productBought;
    }
}

