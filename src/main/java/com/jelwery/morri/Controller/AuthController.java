package com.jelwery.morri.Controller;

import com.jelwery.morri.DTO.LoginDTO;
import com.jelwery.morri.Model.User;
import com.jelwery.morri.Service.AuthService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
@CrossOrigin(origins = "http://localhost:3000")  
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    //register
    @PostMapping("/register")
    public User register(@Valid @RequestBody User user) throws Exception {
        System.out.println(user);
        return authService.reegisterUser(user);
    }
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginDTO loginDTO) throws Exception {
        return authService.loginUser(loginDTO );
    }
    @PostMapping("resetPassword")
    public ResponseEntity<?> resetPassword(@RequestParam String email, @RequestParam String newPassword) {
         return authService.resetPassword(email, newPassword);
    }
}
