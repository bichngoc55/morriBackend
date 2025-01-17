package com.jelwery.morri.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jelwery.morri.DTO.CustomerDTO;
import com.jelwery.morri.Model.Customer;
import com.jelwery.morri.Model.ROLE;
import com.jelwery.morri.Repository.CustomerRepository;
import com.mongodb.DuplicateKeyException;

@Service
public class CustomerService {
    private final BCryptPasswordEncoder passwordEncoder =new BCryptPasswordEncoder()  ;


    @Autowired
    private CustomerRepository customerRepository;

    //create
    public Customer createCustomer(CustomerDTO customerDTO) throws Exception {
        try { 
            Optional<Customer> existing = customerRepository.findByPhoneNumber(customerDTO.getPhoneNumber());
            if(existing.isPresent()) {
                throw new Exception("Số điện thoại đã tồn tại");
            } 
            Customer customer = new Customer();
            customer.setName(customerDTO.getName());
            customer.setGioiTinh(customerDTO.getGioiTinh());
            customer.setPhoneNumber(customerDTO.getPhoneNumber());
            customer.setDateOfBirth(customerDTO.getDateOfBirth());
            customer.setEmail(customerDTO.getEmail());
            customer.setNgayDangKyThanhVien(customerDTO.getNgayDangKyThanhVien());
            customer.setRole(ROLE.CUSTOMER);  
            customer.setDanhSachSanPhamDaMua(new ArrayList<>()); 
             
            if(customerDTO.getPassword() != null) {
                customer.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
            }
            
            return customerRepository.save(customer);
        } catch (DuplicateKeyException e) {
            throw new Exception("Số điện thoại đã tồn tại");
        }
    }
   public Customer updateCustomer(String customerId, CustomerDTO customerDTO) throws Exception {
    Optional<Customer> customerOpt = customerRepository.findById(customerId);
    
    if (customerOpt.isPresent()) {
        Customer existingCustomer = customerOpt.get();
         
        if (customerDTO.getPhoneNumber() != null && 
            !existingCustomer.getPhoneNumber().equals(customerDTO.getPhoneNumber())) {
            Optional<Customer> phoneConflict = customerRepository.findByPhoneNumber(customerDTO.getPhoneNumber());
            if (phoneConflict.isPresent()) {
                throw new Exception("Số điện thoại đã tồn tại");
            }
        }
         
        if (customerDTO.getName() != null) {
            existingCustomer.setName(customerDTO.getName());
        }
        if (customerDTO.getGioiTinh() != null) {
            existingCustomer.setGioiTinh(customerDTO.getGioiTinh());
        }
        if (customerDTO.getPhoneNumber() != null) {
            existingCustomer.setPhoneNumber(customerDTO.getPhoneNumber());
        }
        if (customerDTO.getDateOfBirth() != null) {
            existingCustomer.setDateOfBirth(customerDTO.getDateOfBirth());
        }
        if (customerDTO.getEmail() != null) {
            existingCustomer.setEmail(customerDTO.getEmail());
        }
        if (customerDTO.getNgayDangKyThanhVien() != null) {
            existingCustomer.setNgayDangKyThanhVien(customerDTO.getNgayDangKyThanhVien());
        }
        
        return customerRepository.save(existingCustomer);
    } else {
        throw new Exception("Customer with ID " + customerId + " not found");
    }
}
    public List<Customer> getAllCustomers() throws Exception {
        return customerRepository.findAll();
    }
    public String deleteCustomer(String customerId) throws Exception {
        Optional<Customer> customerOpt = customerRepository.findById(customerId);

        if (customerOpt.isPresent()) {
            customerRepository.deleteById(customerId);
            return "Customer deleted successfully";
        } else {
            throw new Exception("Customer with ID " + customerId + " not found");
        }
    }
    public Customer getOneCustomer(String customerId) throws Exception {
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (customerOpt.isPresent()) {
            return customerOpt.get();
        }
        else {
            throw new Exception("Customer with ID " + customerId + " not found");
        }
    }

    public Customer getCustomerByPhone(String customerPhone) throws Exception {
        Optional<Customer> customerOpt = customerRepository.findByPhoneNumber(customerPhone);
        if (customerOpt.isPresent()) {
            return customerOpt.get();
        }
        else {
            throw new Exception("Customer with phone " + customerPhone + " not found");
        }
    }
}
