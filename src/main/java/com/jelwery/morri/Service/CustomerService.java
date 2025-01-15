package com.jelwery.morri.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jelwery.morri.Model.Customer;
import com.jelwery.morri.Repository.CustomerRepository;
import com.mongodb.DuplicateKeyException;

@Service
public class CustomerService {
    private final BCryptPasswordEncoder passwordEncoder =new BCryptPasswordEncoder()  ;


    @Autowired
    private CustomerRepository customerRepository;

    //create
    public Customer createCustomer(Customer customer) throws Exception {
        try {
            if(customer.getPassword() != null) {
                customer.setPassword(passwordEncoder.encode(customer.getPassword()));
            }
            Optional<Customer> existing = customerRepository.findByPhoneNumber(customer.getPhoneNumber());
            if(existing.isPresent()) {
                throw new Exception("Số điện thoại đã tồn tại");
            }
            
            return customerRepository.save(customer);
        } catch (DuplicateKeyException e) {
            throw new Exception("Số điện thoại đã tồn tại");
        }
    }
    public Customer updateCustomer(String customerId, Customer customer) throws Exception {
        Optional<Customer> existingCustomerOpt = customerRepository.findById(customerId);

        if (existingCustomerOpt.isPresent()) {
            Customer existingCustomer = existingCustomerOpt.get();

            if (customer.getName() != null) {
                existingCustomer.setName(customer.getName());
            }
            if (customer.getEmail() != null) {
                existingCustomer.setEmail(customer.getEmail());
            }
            if (customer.getPhoneNumber() != null) {
                existingCustomer.setPhoneNumber(customer.getPhoneNumber());
            }
            if (customer.getGioiTinh() != null) {
                existingCustomer.setGioiTinh(customer.getGioiTinh());
            }
            if (customer.getDateOfBirth() != null) {
                existingCustomer.setDateOfBirth(customer.getDateOfBirth());
            }
            if (customer.getNgayDangKyThanhVien() != null) {
                existingCustomer.setNgayDangKyThanhVien(customer.getNgayDangKyThanhVien());
            }
            if (customer.getDanhSachSanPhamDaMua() != null) {
                existingCustomer.setDanhSachSanPhamDaMua(customer.getDanhSachSanPhamDaMua());
            }

            return customerRepository.save(existingCustomer);
        } else {
            throw new Exception("Customer with ID " + customer.getId() + " not found");
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
