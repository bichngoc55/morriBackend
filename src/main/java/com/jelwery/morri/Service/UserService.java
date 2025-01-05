package com.jelwery.morri.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jelwery.morri.Model.User;
import com.jelwery.morri.Repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
 
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
 
    public User createUser(User user) { 
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
 
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }
 
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
 
    public User updateUser(String id, User user) { 
        Optional<User> existingUserOpt = userRepository.findById(id);
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get(); 
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());
            existingUser.setName(user.getName());
            existingUser.setDateOfBirth(user.getDateOfBirth());
            existingUser.setGender(user.getGender());
            existingUser.setPhoneNumber(user.getPhoneNumber());
            existingUser.setCccd(user.getCccd());
            existingUser.setAvaURL(user.getAvaURL());
            existingUser.setAddress(user.getAddress());
            existingUser.setRole(user.getRole());
            existingUser.setLuongCoBan(user.getLuongCoBan());

            return userRepository.save(existingUser);
        } else {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
    }
 
    public void deleteUser(String id) { 
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
    }

}
