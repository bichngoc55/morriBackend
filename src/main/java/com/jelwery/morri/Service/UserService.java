package com.jelwery.morri.Service;

import com.jelwery.morri.Model.Absence;
import com.jelwery.morri.Model.Salary;
import com.jelwery.morri.Model.User;
import com.jelwery.morri.Repository.AbsenceRepository;
import com.jelwery.morri.Repository.SalaryRepository;
import com.jelwery.morri.Repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SalaryRepository salaryRepository;
     
    @Autowired
    private AbsenceRepository absenceRepository;
    private final BCryptPasswordEncoder passwordEncoder =new BCryptPasswordEncoder()  ;


    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
         
        for (User user : users) {
            populateUserRelatedData(user);
        }
        
        return users;
    }

    public User getUserById(String id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
             
        populateUserRelatedData(user);
        
        return user;
    }

    public User createUser(User user) {
        validateUser(user);
         
        user.setPassword(passwordEncoder.encode(user.getPassword()));
         
        user.setNgayVaoLam(LocalDateTime.now());
         
        if (user.getAbsencesId() == null) {
            user.setAbsencesId(new ArrayList<>());
        }
        if (user.getBangLuongId() == null) {
            user.setBangLuongId(new ArrayList<>());
        }
        
        return userRepository.save(user);
    }

    public User updateUser(String id, User user) {
        User existingUser = getUserById(id);
         
        user.setId(id);
        user.setNgayVaoLam(existingUser.getNgayVaoLam());
         
        if (user.getPassword().equals(existingUser.getPassword())) {
            user.setPassword(existingUser.getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        
        validateUser(user);
        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    private void populateUserRelatedData(User user) { 
        if (user.getAbsencesId() != null && !user.getAbsencesId().isEmpty()) {
            ArrayList<Absence> absences = new ArrayList<>();
            for (String absenceId : user.getAbsencesId()) {
                absenceRepository.findById(absenceId)
                    .ifPresent(absences::add);
            }
            user.setAbsences(absences);
        } 
        if (user.getBangLuongId() != null && !user.getBangLuongId().isEmpty()) {
            ArrayList<Salary> salaries = new ArrayList<>();
            for (String salaryId : user.getBangLuongId()) {
                salaryRepository.findById(salaryId)
                    .ifPresent(salaries::add);
            }
            user.setBangLuong(salaries);
        }
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
 
        // if (user.getId() == null) { 
        //     if (userRepository.existsByEmail(user.getEmail())) {
        //         throw new IllegalArgumentException("Email already exists");
        //     }
        //     if (userRepository.existsByUsername(user.getUsername())) {
        //         throw new IllegalArgumentException("Username already exists");
        //     }
        //     if (user.getCccd() != null && userRepository.existsByCccd(user.getCccd())) {
        //         throw new IllegalArgumentException("CCCD already exists");
        //     }
        // } else { 
        //     userRepository.findByEmail(user.getEmail())
        //         .ifPresent(existingUser -> {
        //             if (!existingUser.getId().equals(user.getId())) {
        //                 throw new IllegalArgumentException("Email already exists");
        //             }
        //         });
        //     userRepository.findByUsername(user.getUsername())
        //         .ifPresent(existingUser -> {
        //             if (!existingUser.getId().equals(user.getId())) {
        //                 throw new IllegalArgumentException("Username already exists");
        //             }
        //         });
        //     if (user.getCccd() != null) {
        //         userRepository.findByCccd(user.getCccd())
        //             .ifPresent(existingUser -> {
        //                 if (!existingUser.getId().equals(user.getId())) {
        //                     throw new IllegalArgumentException("CCCD already exists");
        //                 }
        //             });
        //     }
        // }
    }

}
