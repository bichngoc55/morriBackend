package com.jelwery.morri.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jelwery.morri.Model.Customer;
import com.jelwery.morri.Model.User;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    Optional<User> findByEmail(String email);
    Optional<Customer> findByPhoneNumber (String phoneNumber);
    
}
