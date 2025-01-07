package com.jelwery.morri.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.BillMua;
import com.jelwery.morri.Model.Product;
import com.jelwery.morri.Model.ProductBoughtFromCustomer;
import com.jelwery.morri.Repository.BillMuaRepository;
import com.jelwery.morri.Repository.ProductBoughtFromCustomerRepository;
import com.jelwery.morri.Repository.ProductRepository;

@Service
public class BillMuaService {
   @Autowired
    private BillMuaRepository billMuaRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private ProductBoughtFromCustomerRepository productBoughtFromCustomerRepository;

    public BillMua createBill(BillMua bill) { 
        if (bill.getTotalPrice() == null || bill.getTotalPrice() == 0) {
            double total = bill.getDsSanPhamDaMua().stream()
                .mapToDouble(productBoughtFromCustomer -> {
                    Product product = productRepository.findById(productBoughtFromCustomer.getProductId().getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
                    return product.getCostPrice() * productBoughtFromCustomer.getQuantity();
                })
                .sum();
            bill.setTotalPrice(total);
        }
 
        bill.setCreatedAt(LocalDateTime.now());
         
        if (bill.getStatus() == null) {
            bill.setStatus(50); 
        } 
        List<ProductBoughtFromCustomer> savedProducts = new ArrayList<>();
        for (ProductBoughtFromCustomer productBought : bill.getDsSanPhamDaMua()) { 
            Product product = productRepository.findById(productBought.getProductId().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
            product.setQuantity(product.getQuantity() + productBought.getQuantity());
            productRepository.save(product); 
            ProductBoughtFromCustomer savedProductBought = productBoughtFromCustomerRepository.save(productBought);
            savedProducts.add(savedProductBought);
        }
        
        bill.setDsSanPhamDaMua(new ArrayList<>(savedProducts));
        return billMuaRepository.save(bill);
    }

    public List<BillMua> getAllBills() {
        return billMuaRepository.findAll();
    }

    public BillMua getBillById(String id) {
        return billMuaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Bill not found with id: " + id));
    }

    public BillMua updateBill(String id, BillMua updatedBill) {
        BillMua existingBill = getBillById(id);
         
        if (updatedBill.getTotalPrice() != null) {
            existingBill.setTotalPrice(updatedBill.getTotalPrice());
        }
        if (updatedBill.getCustomerId() != null) {
            existingBill.setCustomerId(updatedBill.getCustomerId());
        }
        if (updatedBill.getStatus() != null) {
            existingBill.setStatus(updatedBill.getStatus());
        }
        if (updatedBill.getStaffId() != null) {
            existingBill.setStaffId(updatedBill.getStaffId());
        }
        
        return billMuaRepository.save(existingBill);
    }

    public void deleteBill(String id) {
        BillMua existingBill = getBillById(id);
         
        for (ProductBoughtFromCustomer productBought : existingBill.getDsSanPhamDaMua()) {
            Product product = productRepository.findById(productBought.getProductId().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
            product.setQuantity(product.getQuantity() - productBought.getQuantity());
            productRepository.save(product); 
            productBoughtFromCustomerRepository.delete(productBought);
        }
        
        billMuaRepository.delete(existingBill);
    }
  
 
}