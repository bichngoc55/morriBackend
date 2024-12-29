package com.jelwery.morri.Service;

import com.jelwery.morri.Model.User;
import com.jelwery.morri.Repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private MongoTemplate mongoTemplate;

    private final BCryptPasswordEncoder passwordEncoder =new BCryptPasswordEncoder()  ;


    public List<User> getAllUsers() {
        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.lookup("absences", "absencesId", "_id", "absences"),
            Aggregation.lookup("salaries", "bangLuongId", "_id", "bangLuong")
        );
        
        AggregationResults<User> results = mongoTemplate.aggregate(
            aggregation, "users", User.class);
            
        return results.getMappedResults();
    }

    public User getUserById(String id) {
        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.match(Criteria.where("_id").is(id)),
            Aggregation.lookup("absences", "absencesId", "_id", "absences"),
            Aggregation.lookup("salaries", "bangLuongId", "_id", "bangLuong")
        );
        
        AggregationResults<User> results = mongoTemplate.aggregate(
            aggregation, "users", User.class);
        
        User result = results.getUniqueMappedResult();
        if (result == null) {
            throw new RuntimeException("User not found with id: " + id);
        }
        return result;
    }

    public User createUser(User user) {
        validateUser(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User saved = mongoTemplate.save(user);
        return getUserById(saved.getId());
    }

    public User updateUser(String id, User user) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        user.setId(id);
        validateUser(user);
        User saved = mongoTemplate.save(user);
        return getUserById(saved.getId());
    }

    public void deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    private void validateUser(User user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long");
        }
 
        if (user.getId() == null) { 
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new IllegalArgumentException("Email already exists");
            }
            if (userRepository.existsByUsername(user.getUsername())) {
                throw new IllegalArgumentException("Username already exists");
            }
            if (user.getCccd() != null && userRepository.existsByCccd(user.getCccd())) {
                throw new IllegalArgumentException("CCCD already exists");
            }
        } else { 
            userRepository.findByEmail(user.getEmail())
                .ifPresent(existingUser -> {
                    if (!existingUser.getId().equals(user.getId())) {
                        throw new IllegalArgumentException("Email already exists");
                    }
                });
            userRepository.findByUsername(user.getUsername())
                .ifPresent(existingUser -> {
                    if (!existingUser.getId().equals(user.getId())) {
                        throw new IllegalArgumentException("Username already exists");
                    }
                });
            if (user.getCccd() != null) {
                userRepository.findByCccd(user.getCccd())
                    .ifPresent(existingUser -> {
                        if (!existingUser.getId().equals(user.getId())) {
                            throw new IllegalArgumentException("CCCD already exists");
                        }
                    });
            }
        }
    }

}
