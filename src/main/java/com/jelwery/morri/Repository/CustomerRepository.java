package com.jelwery.morri.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jelwery.morri.Model.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    // Optional<User> findByEmail(String email);
    Optional<Customer> findByPhoneNumber (String phoneNumber);
    
}
