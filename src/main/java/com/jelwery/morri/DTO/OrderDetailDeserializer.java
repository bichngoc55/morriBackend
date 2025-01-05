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
import com.jelwery.morri.Model.User;
import com.jelwery.morri.Repository.OrderDetailRepository;
import com.jelwery.morri.Repository.ProductRepository;
import com.jelwery.morri.Repository.UserRepository;

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
         
        String productId = node.get("product").get("id").asText();
        int quantity = node.get("quantity").asInt();
         
        String id = node.has("id") ? node.get("id").asText() : null; 
        OrderDetail orderDetail = null;

        if (id != null) {
            orderDetail = orderDetailRepository.findById(id)
                    .orElse(null);  
        }

        if (orderDetail == null) { 
            orderDetail = new OrderDetail(); 
             Product product=   productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
                
            orderDetail.setProduct(product);
            orderDetail.setQuantity(quantity);
            orderDetail.setUnitPrice(product.getSellingPrice());
            orderDetail.setSubtotal(quantity * orderDetail.getUnitPrice()); 
                        // orderDetail.setSubtotal(orderDetail.getQuantity() * orderDetail.getUnitPrice());

        } else { 
            orderDetail.setQuantity(quantity); 
            orderDetail.setSubtotal(quantity * orderDetail.getUnitPrice());
        }

        return orderDetail;
    }

}
