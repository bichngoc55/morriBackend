package com.jelwery.morri.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.BillBan;
import com.jelwery.morri.Model.BillBan.BillStatus;
import com.jelwery.morri.Model.Customer;
import com.jelwery.morri.Model.Product;
import com.jelwery.morri.Model.User;
import com.jelwery.morri.Repository.BillBanRepository;
import com.jelwery.morri.Repository.CustomerRepository;
import com.jelwery.morri.Repository.ProductRepository;
import com.jelwery.morri.Repository.UserRepository;

@Service
@RestController
@RequestMapping("/billBan")
public class BillBanService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BillBanRepository billBanRepository;
    @Autowired
    private ProductRepository productRepository;
   
    public BillBan createBillBan(BillBan billBan) {
        // Staff validation
        if (billBan.getStaff() != null && billBan.getStaff().getEmail() != null) {
            User staff = userRepository.findByEmail(billBan.getStaff().getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Staff not found with email: " + billBan.getStaff().getEmail()));
            billBan.setStaff(staff);
        } else {
            billBan.setStaff(null);
        }
    
        // Customer validation
        if (billBan.getCustomer() == null || billBan.getCustomer().getPhoneNumber() == null) {
            throw new IllegalArgumentException("Customer phone number is required");
        }
        
        Customer customer = customerRepository.findByPhoneNumber(billBan.getCustomer().getPhoneNumber())
            .orElseThrow(() -> new IllegalArgumentException("Customer not found with phone number: " + 
                billBan.getCustomer().getPhoneNumber()));
    
        billBan.setCustomer(customer);
        billBan.setCreateAt(LocalDateTime.now());
    
        // Validate and process order details
        if (billBan.getOrderDetails() == null || billBan.getOrderDetails().isEmpty()) {
            throw new IllegalArgumentException("Order details cannot be empty");
        }
    
        double calculatedTotal = 0.0;
        for (BillBan.OrderDetail orderDetail : billBan.getOrderDetails()) {
            if (orderDetail.getProduct() == null || orderDetail.getProduct().getId() == null) {
                throw new IllegalArgumentException("Invalid product information in order details");
            }
    
            Product product = productRepository.findById(orderDetail.getProduct().getId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + 
                    orderDetail.getProduct().getId()));
    
            // Validate inventory
            if (orderDetail.getQuantity() > product.getQuantity()) {
                throw new IllegalArgumentException("Insufficient inventory for product: " + 
                    product.getName() + ". Available: " + product.getQuantity() + 
                    ", Requested: " + orderDetail.getQuantity());
            }
    
            orderDetail.setProduct(product);
            calculatedTotal += orderDetail.getSubtotal();
        }
    
        // Validate total price
        if (Math.abs(billBan.getTotalPrice() - calculatedTotal) > 0.01) {
            throw new IllegalArgumentException("Total price mismatch. Calculated: " + 
                calculatedTotal + ", Provided: " + billBan.getTotalPrice());
        }
    
        // Save bill
        BillBan savedBillBan = billBanRepository.save(billBan);
    
        // Update customer purchase history
        ArrayList<String> purchaseHistory = customer.getDanhSachSanPhamDaMua();
        if (purchaseHistory == null) {
            purchaseHistory = new ArrayList<>();
        }
        purchaseHistory.add(savedBillBan.getId());
        customer.setDanhSachSanPhamDaMua(purchaseHistory);
        customerRepository.save(customer);
    
        // Update inventory
        for (BillBan.OrderDetail orderDetail : billBan.getOrderDetails()) {
            Product product = orderDetail.getProduct();
            product.setQuantity(product.getQuantity() - orderDetail.getQuantity());
            productRepository.save(product);

        }
    
        return savedBillBan;
    }
    
     public List<BillBan> getAllBillBan() {
        return billBanRepository.findAll();
    } 
    public BillBan getBillBanById(String id) {
        return billBanRepository.findById(id).orElse(null);
    }

    public BillBan updateBillBanStatus(String billBanId, BillStatus newStatus) {
        BillBan existingBillBan = billBanRepository.findById(billBanId)
                .orElseThrow(() -> new ResourceNotFoundException("Bill ban not found with id: " + billBanId));
    
        if (newStatus != null && newStatus.equals(BillStatus.CANCELLED)) {
            for (BillBan.OrderDetail orderDetail : existingBillBan.getOrderDetails()) {
                Product product = orderDetail.getProduct();
                product.setQuantity(product.getQuantity() + orderDetail.getQuantity());
                productRepository.save(product); 
            }
            billBanRepository.deleteById(billBanId);
            return existingBillBan; 
        }
    
        if (newStatus != null) {
            existingBillBan.setStatus(newStatus);
        }
    
        return billBanRepository.save(existingBillBan);
    }
    
    public BillBan updateBillBan(String billBanId, BillBan updatedBillBan) {  
        BillBan existingBillBan = billBanRepository.findById(billBanId)
                .orElseThrow(() -> new ResourceNotFoundException("Bill ban not found with id: " + billBanId));

        if (updatedBillBan.getCustomer() != null) {
            existingBillBan.setCustomer(updatedBillBan.getCustomer());
        }
        if (updatedBillBan.getOrderDetails() != null) {
            existingBillBan.setOrderDetails(updatedBillBan.getOrderDetails());
        }
      
        if (updatedBillBan.getStaff() != null) {
            existingBillBan.setStaff(updatedBillBan.getStaff());
        }

        if (updatedBillBan.getStatus() != null) {
            existingBillBan.setStatus(updatedBillBan.getStatus());
        }
        if (updatedBillBan.getTotalPrice() != null) {
            existingBillBan.setTotalPrice(updatedBillBan.getTotalPrice());
        }

        if (updatedBillBan.getPaymentMethod() != null) {
            existingBillBan.setPaymentMethod(updatedBillBan.getPaymentMethod());
        }

        return billBanRepository.save(existingBillBan);
        } 
        
}