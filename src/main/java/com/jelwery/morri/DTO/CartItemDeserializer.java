package com.jelwery.morri.DTO;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jelwery.morri.Model.CartItem;
import com.jelwery.morri.Model.Product;
import com.jelwery.morri.Repository.ProductRepository;
@Component
public class CartItemDeserializer extends JsonDeserializer<CartItem>{
    @Autowired
    private ProductRepository productRepository;
    @Override
    public CartItem deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        
        CartItem cartItem = new CartItem(); 
        JsonNode idNode = node.get("id");
        if (idNode != null && !idNode.isNull()) {
            cartItem.setId(idNode.asText());
        } 
        JsonNode productNode = node.get("product");
        if (productNode != null && !productNode.isNull()) {
            String productId = productNode.asText();  
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product with ID " + productId + " not found"));
            cartItem.setProduct(product);
        }
         
        JsonNode quantityNode = node.get("selectedQuantity");
        if (quantityNode != null && !quantityNode.isNull()) {
            cartItem.setSelectedQuantity(quantityNode.asInt());
        }
        
        return cartItem;
    }
}
