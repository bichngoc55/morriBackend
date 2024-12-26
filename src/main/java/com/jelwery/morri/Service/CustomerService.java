package com.jelwery.morri.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jelwery.morri.Model.Customer;
import com.jelwery.morri.Model.User;
import com.jelwery.morri.Repository.CustomerRepository;

@Service
public class CustomerService {
    private final BCryptPasswordEncoder passwordEncoder =new BCryptPasswordEncoder()  ;


    @Autowired
    private CustomerRepository customerRepository;

    //create
    public Customer createCustomer(Customer customer) throws Exception {
        Optional<User> existingUser = customerRepository.findByEmail(customer.getEmail());
        if (existingUser.isPresent()) {
            throw new Exception("Email is already registered");
        }
        if (customer.getName().isEmpty()  ||
                 customer.getEmail().isEmpty() ||
                customer.getPassword().isEmpty()
                ) {
            throw new Exception("All fields ( name, email, password) are required");
        }
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        customerRepository.save(customer);
        return customerRepository.save(customer);
//        return customerRepository.save(customer);
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
            if (customer.getDanhSachSanPhamDaMua() != null) {
                existingCustomer.setPhoneNumber(customer.getPhoneNumber());
            }
            if (customer.getPhoneNumber() != null) {
                existingCustomer.setPhoneNumber(customer.getPhoneNumber());
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
}
