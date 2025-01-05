package com.jelwery.morri.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jelwery.morri.Model.Inventory;
import com.jelwery.morri.Service.InventoryService;
@CrossOrigin(origins = "*")

@RequestMapping("/inventory")
@RestController
public class InventoryController {
    @Autowired
    private InventoryService inventoryService; 
 
    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventories() {
        List<Inventory> inventories = inventoryService.getAllInventories();
        return ResponseEntity.ok(inventories);
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable String id) {
        Inventory inventory = inventoryService.getInventoryById(id);
        return ResponseEntity.ok(inventory);
    }
 
    @PostMapping("/create")
    public ResponseEntity<Inventory> addInventory(@RequestBody Inventory inventory) {
        Inventory newInventory = inventoryService.addInventory(inventory);
        return ResponseEntity.ok(newInventory);
    }
 
    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable String id, @RequestBody Inventory inventoryDetails) {
        Inventory updatedInventory = inventoryService.updateInventory(id, inventoryDetails);
        return ResponseEntity.ok(updatedInventory);
    }
 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable String id) {
        inventoryService.deleteInventory(id);
        return ResponseEntity.noContent().build();
    }
}
