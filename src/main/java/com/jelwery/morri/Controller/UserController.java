package com.jelwery.morri.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jelwery.morri.Model.User;
import com.jelwery.morri.Service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    // @GetMapping
    // public ResponseEntity<List<User>> getAllUsers() {
    //     return ResponseEntity.ok(userService.getAllUsers());
    // }

    // @GetMapping("/{id}")
    // public ResponseEntity<User> getUserById(@PathVariable String id) {
    //     return ResponseEntity.ok(userService.getUserById(id));
    // }

    // @PostMapping
    // public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
    //     return ResponseEntity.ok(userService.createUser(user));
    // }

    // @PutMapping("/{id}")
    // public ResponseEntity<User> updateUser(@PathVariable String id, @Valid @RequestBody User user) {
    //     return ResponseEntity.ok(userService.updateUser(id, user));
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> deleteUser(@PathVariable String id) {
    //     userService.deleteUser(id);
    //     return ResponseEntity.ok().build();
    // }
}
