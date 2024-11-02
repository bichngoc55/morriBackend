package com.jelwery.morri.Validation;

import org.springframework.stereotype.Component;

import com.jelwery.morri.Exception.ValidationException;
import com.jelwery.morri.Model.Inventory;

@Component
public class InventoryValidation {
    public void validateInventoryDTO(Inventory inventoryDTO) {
        // if (inventoryDTO == null) {
        //     throw new ValidationException("Inventory data cannot be null");
        // }

        // if (inventoryDTO.getName() == null || inventoryDTO.getName().trim().isEmpty()) {
        //     throw new ValidationException("Inventory name is required");
        // }

        if (inventoryDTO.getQuantity() < 0) {
            throw new ValidationException("Quantity cannot be negative");
        }

  
        if (inventoryDTO.getTotalPrice() == null || inventoryDTO.getTotalPrice() < 0) {
            throw new ValidationException("Total price must be valid and non-negative");
        }

        if (inventoryDTO.getInventoryProducts() == null || inventoryDTO.getInventoryProducts().isEmpty()) {
            throw new ValidationException("Inventory must contain at least one product");
        }

     
    }

}
