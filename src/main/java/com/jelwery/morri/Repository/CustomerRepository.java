package com.jelwery.morri.Repository;

import com.jelwery.morri.Model.Customer;
import com.jelwery.morri.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    Optional<User> findByEmail(String email);
}
