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
@RestController
@RequestMapping("/inventory")
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;
    // @Autowired
    // private SupplierRespository supplierRespository;
    // @Autowired
    // private UserRepository userRepository;
    
    @Autowired
    private InventoryValidation inventoryValidation;

    // @GetMapping("/")
    // public ResponseEntity<?> accessSalesGateway(HttpServletRequest request) { 
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     System.out.println(authentication);
    //     String userEmail = authentication.getName();
        
    //     // Your sales gateway logic here
    //     return ResponseEntity.ok(Map.of(
    //         "message", "Successfully accessed sales gateway",
    //         "userEmail", userEmail
    //     ));
    // }
    public Inventory createInventory(Inventory inventoryDTO) {
        // inventoryValidation.validateInventoryDTO(inventoryDTO);
        
        // Inventory inventory = new Inventory();
        // mapDTOToInventory(inventoryDTO, inventory);
        inventoryDTO.setNgayNhapKho(LocalDateTime.now());
        return inventoryRepository.save(inventoryDTO);
    }

    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    public Inventory getInventoryById(String id) {
        return inventoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Inventory not found with id: " + id));
    }

    public Inventory updateInventory(String id, Inventory inventoryDTO) {
        Inventory existingInventory = getInventoryById(id);
        
        inventoryValidation.validateInventoryDTO(inventoryDTO);
        
        if (inventoryDTO.getName() != null) {
            existingInventory.setName(inventoryDTO.getName());
        }
        if (inventoryDTO.getQuantity() > 0) {
            existingInventory.setQuantity(inventoryDTO.getQuantity());
        }
        // if (inventoryDTO.getSupplierId() != null) {
        //     Supplier supplier = supplierRespository.findById(inventoryDTO.getSupplierId())
        //          .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + inventoryDTO.getSupplierId()));
        //     existingInventory.setSupplierId(supplier);
        // }
        // if (inventoryDTO.getUserId() != null) {
        //     User user = userRepository.findById(inventoryDTO.getUserId())
        //         .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        //     existingInventory.setUserId(user);
        // }
        if (inventoryDTO.getTotalPrice() != null) {
            existingInventory.setTotalPrice(inventoryDTO.getTotalPrice());
        }
        if (inventoryDTO.getInventoryProducts() != null) {
            existingInventory.setInventoryProducts(inventoryDTO.getInventoryProducts());
        }
        
        return inventoryRepository.save(existingInventory);
    }

    public void deleteInventory(String id) {
        if (!inventoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Inventory not found with id: " + id);
        }
        inventoryRepository.deleteById(id);
    }

  
}
