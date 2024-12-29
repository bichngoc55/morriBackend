package com.jelwery.morri.DTO;

import java.io.IOException; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jelwery.morri.Repository.SupplierRespository;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.Supplier; 
import com.fasterxml.jackson.core.JsonParser; 

@Component
public class SupplierDeserializer extends JsonDeserializer<Supplier>{
     @Autowired
    private SupplierRespository supplierRepository;

    @Override
    public Supplier deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String supplierId = p.getText();
        return supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + supplierId));
    }

}