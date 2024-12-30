package com.jelwery.morri.Service;


import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.Inventory;
import com.jelwery.morri.Model.Supplier;
import com.jelwery.morri.Model.User;
import com.jelwery.morri.Repository.InventoryRepository;
import com.jelwery.morri.Repository.SupplierRespository;
import com.jelwery.morri.Repository.UserRepository;
import com.jelwery.morri.Validation.InventoryValidation;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.Authentication;

@Service 
public class InventoryService {
    // @Autowired
    // private InventoryRepository inventoryRepository;
 
    // public List<Inventory> getAllInventories() {
    //     return inventoryRepository.findAll();
    // }
 
    // public Inventory getInventoryById(String id) {
    //     return inventoryRepository.findById(id).orElseThrow(() -> 
    //         new RuntimeException("Inventory not found with ID: " + id));
    // }
 
    // public Inventory addInventory(Inventory inventory) {
    //     return inventoryRepository.save(inventory);
    // }
 
    // public Inventory updateInventory(String id, Inventory inventoryDetails) {
    //     Inventory existingInventory = getInventoryById(id);
    //     existingInventory.setName(inventoryDetails.getName());
    //     existingInventory.setQuantity(inventoryDetails.getQuantity());
    //     existingInventory.setSupplierId(inventoryDetails.getSupplierId());
    //     existingInventory.setUserId(inventoryDetails.getUserId());
    //     existingInventory.setTotalPrice(inventoryDetails.getTotalPrice());
    //     existingInventory.setInventoryProducts(inventoryDetails.getInventoryProducts());
    //     return inventoryRepository.save(existingInventory);
    // }
 
    // public void deleteInventory(String id) {
    //     inventoryRepository.deleteById(id);
    // }
    @Autowired
    private InventoryRepository inventoryRepository;
    
    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    public Inventory getInventoryById(String id) {
        return inventoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Inventory not found with ID: " + id));
    }

    public Inventory addInventory(Inventory inventory) {
        System.out.println("Inventory : " + inventory);
        inventory.setNgayNhapKho(LocalDateTime.now());
        
        // Calculate quantity from products if not set
        if (inventory.getQuantity() == 0 && inventory.getInventoryProducts() != null) {
            inventory.setQuantity(inventory.getInventoryProducts().size());
        }
        
        return inventoryRepository.save(inventory);
    }

    public Inventory updateInventory(String id, Inventory inventoryDetails) {
        Inventory existingInventory = getInventoryById(id);
        
        if (inventoryDetails.getName() != null) {
            existingInventory.setName(inventoryDetails.getName());
        }
        if (inventoryDetails.getQuantity() > 0) {
            existingInventory.setQuantity(inventoryDetails.getQuantity());
        }
        if (inventoryDetails.getSupplier() != null) {
            existingInventory.setSupplier(inventoryDetails.getSupplier());
        }
        if (inventoryDetails.getUser() != null) {
            existingInventory.setUser(inventoryDetails.getUser());
        }
        if (inventoryDetails.getTotalPrice() != null) {
            existingInventory.setTotalPrice(inventoryDetails.getTotalPrice());
        }
        if (inventoryDetails.getInventoryProducts() != null) {
            existingInventory.setInventoryProducts(inventoryDetails.getInventoryProducts());
        }
        
        return inventoryRepository.save(existingInventory);
    }

    public void deleteInventory(String id) {
        if (!inventoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Inventory not found with ID: " + id);
        }
        inventoryRepository.deleteById(id);
    }
}
