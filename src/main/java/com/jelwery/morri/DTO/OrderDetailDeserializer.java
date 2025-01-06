package com.jelwery.morri.DTO;

import java.io.IOException;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.OrderDetail;
import com.jelwery.morri.Model.Product;
import com.jelwery.morri.Repository.OrderDetailRepository;
import com.jelwery.morri.Repository.ProductRepository;

@Component
public class OrderDetailDeserializer extends JsonDeserializer<OrderDetail>{
  @Autowired
    private  OrderDetailRepository orderDetailRepository;
    @Autowired ProductRepository productRepository;

    // @Override
    // public OrderDetail deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    //     String Id = p.getText();
    //     return orderDetailRepository.findById(Id)
    //             .orElseThrow(() -> new ResourceNotFoundException("order details not found with id: " + Id));

    // }
     @Override
    public OrderDetail deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        String productId;
        int quantity = 1;
        int unitPrice = 1;
        int subTotal = 1;

        if (node.isObject() && node.has("product")) {
            productId = node.get("product").asText();
        } else {
            throw new IllegalArgumentException("Expected object with productId field");
        }

        if (node.has("quantity")) {
            quantity = node.get("quantity").asInt(); // Ensure quantity is properly extracted
        }

        if (node.has("unitPrice")) {
            unitPrice = node.get("unitPrice").asInt(); // Ensure quantity is properly extracted
        }

        // Use ProductRepository to find the Product entity
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        // Create a new ProductBoughtFromCustomer and map the fields
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProduct(product);
        orderDetail.setQuantity(quantity);
        orderDetail.setUnitPrice(unitPrice);
        orderDetail.setSubtotal(quantity * unitPrice);
        return orderDetail;
    }

}
