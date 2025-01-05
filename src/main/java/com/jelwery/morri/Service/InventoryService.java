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
import com.jelwery.morri.Model.InventoryProduct;
import com.jelwery.morri.Model.OrderDetail;
import com.jelwery.morri.Model.Product;
import com.jelwery.morri.Repository.BillBanRepository;
import com.jelwery.morri.Repository.InventoryProductRepository;
import com.jelwery.morri.Repository.InventoryRepository;
import com.jelwery.morri.Repository.ProductRepository;

@Service 
public class InventoryService {
    
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BillBanRepository billBanRepository;
    @Autowired
    private BillBanService billBanService;
    @Autowired 
    private InventoryProductRepository inventoryProductRepository;
    
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
        
     
        if (inventory.getInventoryProducts() != null) {
            ArrayList<InventoryProduct> savedProducts = new ArrayList<>();
            int totalQuantity = 0;
            double totalPrice = 0.0;
            
            for (InventoryProduct invProduct : inventory.getInventoryProducts()) { 
                Product product = productRepository.findById(invProduct.getProduct().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + 
                        invProduct.getProduct().getId()));
                
                invProduct.setProduct(product);
                 
                InventoryProduct savedInvProduct = inventoryProductRepository.save(invProduct);
                savedProducts.add(savedInvProduct);
                 
                product.setQuantity(product.getQuantity() + invProduct.getEnteredQuantity());
                productRepository.save(product);
                
                totalQuantity += invProduct.getEnteredQuantity();
                totalPrice += (product.getSellingPrice() * invProduct.getEnteredQuantity());
            }
            
            inventory.setInventoryProducts(savedProducts);
            inventory.setQuantity(totalQuantity);
            
            if (inventory.getTotalPrice() == null) {
                inventory.setTotalPrice(totalPrice);
            }
        }
        
        return inventoryRepository.save(inventory);
    }

    public Map<Product, Integer> getEnteredQuantityByDate(List<Inventory> inventories) {
        Map<Product, Integer> productQuantities = new HashMap<>();
    
        for (Inventory inventory : inventories) {
            for (InventoryProduct inventoryProduct : inventory.getInventoryProducts()) {
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
        for (OrderDetail orderDetail : bill.getOrderDetails()) {
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
