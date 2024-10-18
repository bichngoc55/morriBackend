package com.jelwery.morri.Repository;

import com.jelwery.morri.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);

}
