package com.jelwery.morri.Service;

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

        User staff = userRepository.findByPhoneNumber(billBan.getStaff().getPhoneNumber())
                .orElseThrow(() -> new IllegalArgumentException("Invalid staff"));
    
        billBan.setStaff(staff);

        // Truy vấn customer từ database với String ID
        Customer customer = customerRepository.findByPhoneNumber(billBan.getCustomer().getPhoneNumber())
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer: "));


        billBan.setCustomer(customer);

        for (BillBan.OrderDetail orderDetail : billBan.getOrderDetails()) {
        Product product = productRepository.findById(orderDetail.getProduct().getId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + 
                    orderDetail.getProduct().getId()));
        
        // Kiem tra so luong con trong kho
        if (orderDetail.getQuantity() > product.getQuantity()) {
            throw new IllegalArgumentException("Insufficient inventory for product: " + 
                product.getName() + ". Available: " + product.getQuantity() + 
                ", Requested: " + orderDetail.getQuantity());
        }
        orderDetail.setProduct(product);
    }
        BillBan savedBillBan = billBanRepository.save(billBan);

          // Cap nhat so luong con trong kho
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

    public BillBan updateBillBanStatus(String billBanId, BillStatus newStatus) {
    BillBan existingBillBan = billBanRepository.findById(billBanId)
            .orElseThrow(() -> new ResourceNotFoundException("Bill ban not found with id: " + billBanId));
    
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
