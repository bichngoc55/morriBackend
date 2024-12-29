package com.jelwery.morri.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jelwery.morri.Model.User;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber); 
    Optional<User> findByUsername(String username);
    Optional<User> findByCccd(String cccd);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByCccd(String cccd);
}
