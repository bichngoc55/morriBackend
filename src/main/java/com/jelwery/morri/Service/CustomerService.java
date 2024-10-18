package com.jelwery.morri.Service;

import com.jelwery.morri.Model.Customer;
import com.jelwery.morri.Model.User;
import com.jelwery.morri.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
}
