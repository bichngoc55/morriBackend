package com.jelwery.morri.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jelwery.morri.Model.Inventory;
import com.jelwery.morri.Service.InventoryService;

@RequestMapping("/inventory")
@RestController
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;
    @PostMapping("/add")
    public ResponseEntity<Inventory> createInventory(@Valid @RequestBody Inventory inventoryDTO) {
        return ResponseEntity.ok(inventoryService.createInventory(inventoryDTO));
    }

    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventories() {
        return ResponseEntity.ok(inventoryService.getAllInventories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable String id) {
        return ResponseEntity.ok(inventoryService.getInventoryById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Inventory> updateInventory(
            @PathVariable String id,
            @Valid @RequestBody Inventory inventoryDTO) {
        return ResponseEntity.ok(inventoryService.updateInventory(id, inventoryDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable String id) {
        inventoryService.deleteInventory(id);
        return ResponseEntity.noContent().build();
    }

}
