package com.jelwery.morri.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.Inventory;
import com.jelwery.morri.Model.Product;
import com.jelwery.morri.Repository.InventoryRepository;
import com.jelwery.morri.Repository.ProductRepository;
import com.jelwery.morri.Validation.InventoryValidation;

@Service
@RestController
@RequestMapping("/inventory")
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
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
         ArrayList<Product> updatedProducts = new ArrayList<>();
        for (Product product : inventoryDTO.getInventoryProducts()) {
            Optional<Product> existingProduct = productRepository.findByName(product.getName());
            if (existingProduct.isPresent()) {
                productService.increaseQuantity(existingProduct.get().getId(), product.getQuantity());
                updatedProducts.add(existingProduct.get());
            } else {
                Product createdProduct = productService.createProduct(product);
                updatedProducts.add(createdProduct);
            }
        }
        inventoryDTO.setNgayNhapKho(LocalDateTime.now());
        inventoryDTO.setInventoryProducts(updatedProducts);
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
