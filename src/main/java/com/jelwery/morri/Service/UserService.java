package com.jelwery.morri.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jelwery.morri.DTO.UserDTO;
import com.jelwery.morri.Model.User;
import com.jelwery.morri.Repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
 
    @Autowired
    BCryptPasswordEncoder passwordEncoder;    
 
    public User createUser(UserDTO userDTO) {
        // Validate required fields
        if (userDTO.getEmail() == null || userDTO.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (userDTO.getUsername() == null || userDTO.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        
        // Check if email already exists
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        // Convert DTO to Entity
        User user = new User();
        updateUser(user, userDTO);
        
        // Set password with encoding
        if (userDTO.getPassword() == null || userDTO.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        
        return userRepository.save(user);
    }
    
    public User updateUser(String id, UserDTO userDTO) {
        Optional<User> existingUserOpt = userRepository.findById(id);
        if (!existingUserOpt.isPresent()) {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
        
        User existingUser = existingUserOpt.get();
        
        // If email is being changed, check if new email already exists
        if (userDTO.getEmail() != null && !userDTO.getEmail().equals(existingUser.getEmail())) {
            if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
                throw new IllegalArgumentException("Email already exists");
            }
        }
        
        // Update user fields
        updateUser(existingUser, userDTO);
        
        return userRepository.save(existingUser);
    }
    
    public void updateUser(User user, UserDTO userDTO) {
        if (userDTO.getUsername() != null) user.setUsername(userDTO.getUsername());
        if (userDTO.getEmail() != null) user.setEmail(userDTO.getEmail());
        if (userDTO.getName() != null) user.setName(userDTO.getName());
        if (userDTO.getDateOfBirth() != null) user.setDateOfBirth(userDTO.getDateOfBirth());
        if (userDTO.getGender() != null) user.setGender(userDTO.getGender());
        if (userDTO.getPhoneNumber() != null) user.setPhoneNumber(userDTO.getPhoneNumber());
        if (userDTO.getCccd() != null) user.setCccd(userDTO.getCccd());
        if (userDTO.getAvaURL() != null) user.setAvaURL(userDTO.getAvaURL());
        if (userDTO.getAddress() != null) user.setAddress(userDTO.getAddress());
        if (userDTO.getRole() != null) user.setRole(userDTO.getRole());
        if (userDTO.getLuongCoBan() != null) user.setLuongCoBan(userDTO.getLuongCoBan());
    }
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
 
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
 
    // public User updateUser(String id, UserDTO userDTO) {
    //     Optional<User> existingUserOpt = userRepository.findById(id);
    //     if (existingUserOpt.isPresent()) {
    //         User existingUser = existingUserOpt.get();
             
    //         if (userDTO.getUsername() != null) {
    //             existingUser.setUsername(userDTO.getUsername());
    //         }
    //         if (userDTO.getEmail() != null) {
    //             existingUser.setEmail(userDTO.getEmail());
    //         }
    //         if (userDTO.getName() != null) {
    //             existingUser.setName(userDTO.getName());
    //         }
    //         if (userDTO.getDateOfBirth() != null) {
    //             existingUser.setDateOfBirth(userDTO.getDateOfBirth());
    //         }
    //         if (userDTO.getGender() != null) {
    //             existingUser.setGender(userDTO.getGender());
    //         }
    //         if (userDTO.getPhoneNumber() != null) {
    //             existingUser.setPhoneNumber(userDTO.getPhoneNumber());
    //         }
    //         if (userDTO.getCccd() != null) {
    //             existingUser.setCccd(userDTO.getCccd());
    //         }
    //         if (userDTO.getAvaURL() != null) {
    //             existingUser.setAvaURL(userDTO.getAvaURL());
    //         }
    //         if (userDTO.getAddress() != null) {
    //             existingUser.setAddress(userDTO.getAddress());
    //         }
    //         if (userDTO.getRole() != null) {
    //             existingUser.setRole(userDTO.getRole());
    //         }
    //         if (userDTO.getLuongCoBan() != null) {
    //             existingUser.setLuongCoBan(userDTO.getLuongCoBan());
    //         }

    //         return userRepository.save(existingUser);
    //     } else {
    //         throw new IllegalArgumentException("User not found with ID: " + id);
    //     }
    // }
 
    public void deleteUser(String id) { 
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
    }

    public void updatePassword(String id, String newPassword) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String encodedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedPassword);
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }
}
