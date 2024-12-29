package com.jelwery.morri.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.jelwery.morri.DTO.LoginDTO;
import com.jelwery.morri.Model.User;
import com.jelwery.morri.Repository.UserRepository;
import com.jelwery.morri.Utils.JWT;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWT jwtUtil;

    @Autowired
    private JavaMailSender mailSender;

    private final BCryptPasswordEncoder passwordEncoder =new BCryptPasswordEncoder()  ;


    public User reegisterUser(User user) throws Exception {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new Exception("Email is already registered");
        }
        if (user.getUsername() == null || user.getUsername().isEmpty() ||
                user.getName() == null || user.getName().isEmpty() ||
                user.getEmail() == null || user.getEmail().isEmpty() ||
                user.getPassword() == null || user.getPassword().isEmpty() ||
                user.getGender() == null) {
            throw new Exception("All fields (username, name, email, password, gender) are required");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
        return userRepository.save(user);

    }
    public Map<String, Object> loginUser(LoginDTO loginDTO)  throws Exception {
        Optional<User> existingUser = userRepository.findByEmail(loginDTO.getEmail() );
        System.out.println(existingUser.isPresent());
        if (existingUser.isPresent() && passwordEncoder.matches(loginDTO.getPassword(), existingUser.get().getPassword())) {
            String token = jwtUtil.generateToken(existingUser.get().getEmail(), existingUser.get().getRole());
            Map<String, Object> response = new HashMap<>();
            response.put("user", existingUser.get());
            response.put("accessToken", token);
            return response;
        } else {
            throw new Exception("Invalid credentials");
        }
    }
    //reset
    public ResponseEntity<?> resetPassword(@RequestParam String email, @RequestParam String newPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return ResponseEntity.ok("Password reset successful");
        }
        return ResponseEntity.status(400).body("Invalid token");
    }
    //forget
    public ResponseEntity<?> sendPasswordResetEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isPresent()) {
            return ResponseEntity.status(400).body("Invalid email");
        }
        User user = userOptional.get();
        String token = jwtUtil.generateToken(user.getEmail(),user.getRole() );

        String resetLink = "http://..." + token;
        // Send the email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Password Reset Request");
        message.setText("To reset your password, click the link below:\n" + resetLink);
        mailSender.send(message);
        return ResponseEntity.ok("Password reset email sent");
    }
}
