package com.jelwery.morri.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jelwery.morri.DTO.InventoryReport;
import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.BillBan;
import com.jelwery.morri.Model.Inventory;
import com.jelwery.morri.Model.Product;
import com.jelwery.morri.Repository.BillBanRepository;
import com.jelwery.morri.Repository.InventoryRepository;

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
    @Autowired
    private ProductService productService;
    @Autowired
    private BillBanRepository billBanRepository;
    @Autowired
    private BillBanService billBanService;
    
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

    public Map<Product, Integer> getEnteredQuantityByDate(List<Inventory> inventories) {
        Map<Product, Integer> productQuantities = new HashMap<>();
    
        for (Inventory inventory : inventories) {
            for (Inventory.InventoryProduct inventoryProduct : inventory.getInventoryProducts()) {
                Product product = inventoryProduct.getProduct();
                int quantity = inventoryProduct.getEnteredQuantity();
    
                // Cộng dồn số lượng sản phẩm cho cùng một sản phẩm
                productQuantities.put(product, productQuantities.getOrDefault(product, 0) + quantity);
            }
        }
    
        return productQuantities;
    }
    
    public Map<Product, Integer> getSoldQuantityByDate(LocalDateTime date) {
    List<BillBan> bills = billBanRepository.findByCreateAt(date); // Giả sử bạn có một repository cho BillBan
    Map<Product, Integer> soldQuantities = new HashMap<>();

    for (BillBan bill : bills) {
        for (BillBan.OrderDetail orderDetail : bill.getOrderDetails()) {
            Product product = orderDetail.getProduct();
            int quantity = orderDetail.getQuantity();

            // Cộng dồn số lượng đã bán cho cùng một sản phẩm
            soldQuantities.put(product, soldQuantities.getOrDefault(product, 0) + quantity);
        }
    }

    return soldQuantities;
}
    public List<InventoryReport> getInventoryByDay(LocalDateTime date) {
        List<Product> products = productService.getAllProducts();
        List<InventoryReport> inventoryByDay = new ArrayList<>();
        LocalDate localDate = date.toLocalDate();
        LocalDateTime startOfDay = localDate.atStartOfDay();
        LocalDateTime endOfDay = localDate.atTime(23, 59, 59);
        // Lấy danh sách Inventory theo khoảng thời gian
        List<Inventory> inventories = inventoryRepository.findByNgayNhapKhoBetween(startOfDay, endOfDay);
        Map<Product, Integer> enteredQuantities = getEnteredQuantityByDate(inventories);
        Map<Product, Integer> soldQuantities = getSoldQuantityByDate(date);
        for (Product product : products) {
            int soLuongTonCuoi = product.getQuantity();
            int soLuongNhap = enteredQuantities.getOrDefault(product, 0); // Lấy số lượng nhập từ map
            int soLuongBan = soldQuantities.getOrDefault(product, 0); // Lấy số lượng bán từ map
            int soLuongTonDau = soLuongTonCuoi + soLuongBan - soLuongNhap;
       
            InventoryReport report = new InventoryReport(product, soLuongTonDau, soLuongNhap, soLuongBan, soLuongTonCuoi);
            inventoryByDay.add(report);
        }
    
        return inventoryByDay;
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
