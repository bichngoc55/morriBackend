package com.jelwery.morri.Controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jelwery.morri.DTO.UserDTO;
import com.jelwery.morri.Model.User;
import com.jelwery.morri.Service.UserService;
 
@RestController
@CrossOrigin(origins = "http://localhost:3000")  

@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
 
    @PostMapping("/create")
    // @CrossOrigin(origins = "*")
    public ResponseEntity<User> createUser( @RequestBody UserDTO user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/getUserByEmail/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.getUserByEmail(email);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
 
    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
 
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody UserDTO user) {
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }
 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<?> updatePassword(
        @PathVariable String id,
        @RequestBody PasswordUpdateRequest request) {
        try {
            userService.updatePassword(id, request.getNewPassword());
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
