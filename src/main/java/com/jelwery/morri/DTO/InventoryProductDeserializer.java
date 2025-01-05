package com.jelwery.morri.DTO;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jelwery.morri.Model.InventoryProduct;
import com.jelwery.morri.Model.Product;
import com.jelwery.morri.Repository.InventoryProductRepository;
import com.fasterxml.jackson.core.JsonParser; 
import com.fasterxml.jackson.databind.DeserializationContext;
import com.jelwery.morri.Exception.ResourceNotFoundException;

import java.io.IOException;
import java.util.ArrayList;
public class InventoryProductDeserializer  extends JsonDeserializer<ArrayList<InventoryProduct> >{
    @Autowired
   private InventoryProductRepository inventoryProductRepository;

 
 @Override
    public ArrayList<InventoryProduct> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        ArrayNode node = mapper.readTree(p);
        ArrayList<InventoryProduct> inventoryProducts = new ArrayList<>();
        
        for (int i = 0; i < node.size(); i++) {
            InventoryProduct inventoryProduct = new InventoryProduct();
            inventoryProduct.setEnteredQuantity(node.get(i).get("enteredQuantity").asInt()); 
            Product product = new Product();
            product.setId(node.get(i).get("product").asText());
            inventoryProduct.setProduct(product);
            inventoryProducts.add(inventoryProduct);
        }
        
        return inventoryProducts;
    }
}
