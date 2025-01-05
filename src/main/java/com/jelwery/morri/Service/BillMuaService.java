package com.jelwery.morri.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.BillMua;
import com.jelwery.morri.Model.Product; 
import com.jelwery.morri.Repository.BillMuaRepository;
import com.jelwery.morri.Repository.ProductRepository;

@Service
public class BillMuaService {
  @Autowired
    private BillMuaRepository billMuaRepository;
    @Autowired
    private ProductRepository productRepository;

    // public BillMua createBill(BillMua bill) { 
    //     if (bill.getTotalPrice() == null || bill.getTotalPrice() == 0) {
    //         double total = bill.getDsSanPhamDaMua().stream()
    //             .mapToDouble(product -> product.getPrice() * product.getQuantity())
    //             .sum();
    //         bill.setTotalPrice(total);
    //     }
    //     return billMuaRepository.save(bill);
    // }

    public BillMua createBill(BillMua bill) { 
        if (bill.getTotalPrice() == null || bill.getTotalPrice() == 0) {
            double total = bill.getDsSanPhamDaMua().stream()
                .mapToDouble(productBoughtFromCustomer -> 
                    productBoughtFromCustomer.getCostPrice() * productBoughtFromCustomer.getQuantity())
                .sum();

            bill.setTotalPrice(total);
        }

        bill.setCreatedAt(LocalDateTime.now());
        for (Product productBoughtFromCustomer : bill.getDsSanPhamDaMua()) {
        Product product = productBoughtFromCustomer;
        int newQuantity = productBoughtFromCustomer.getQuantity();
        
        // Fetch the product from the database
        Product existingProduct = productRepository.findById(product.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + product.getId()));
        
        // Update the quantity of the existing product
        existingProduct.setQuantity(existingProduct.getQuantity() + newQuantity); 
        
        // Save the updated product back to the database
        productRepository.save(existingProduct);
    }
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
        
        existingBill.setTotalPrice(updatedBill.getTotalPrice());
        existingBill.setCustomerId(updatedBill.getCustomerId());
        // existingBill.setCustomerName(updatedBill.getCustomerName());
        // existingBill.setSDT(updatedBill.getSDT());
        // existingBill.setCccd(updatedBill.getCccd());
        existingBill.setStatus(updatedBill.getStatus());
        // existingBill.setDsSanPhamDaMua(updatedBill.getDsSanPhamDaMua());
        existingBill.setStaffId(updatedBill.getStaffId());
        
        return billMuaRepository.save(existingBill);
    }

    public void deleteBill(String id) {
        BillMua existingBill = getBillById(id);
        billMuaRepository.delete(existingBill);
    }
}