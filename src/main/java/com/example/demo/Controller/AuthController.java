package com.example.demo.Controller;

import com.example.demo.DAO.UserRepository;
import com.example.demo.Entity.User;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;



    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestParam String email, @RequestParam String password) {
        Optional<User> userOptional = userService.login(email, password);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/forgot-password")
    public boolean forgotPassword(@RequestParam String email) {
        return userService.forgotPassword(email);
    }

    @GetMapping("/user/store/{storeName}")
    public ResponseEntity<User> getUserByStoreName(@PathVariable String storeName) {
        Optional<User> userOptional = userService.getUserByStoreName(storeName);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{userId}/website-url")
    public ResponseEntity<String> getWebsiteUrl(@PathVariable Long userId) {
        Optional<String> websiteUrl = userService.getWebsiteUrl(userId);
        if (websiteUrl.isPresent()) {
            return ResponseEntity.ok(websiteUrl.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}
