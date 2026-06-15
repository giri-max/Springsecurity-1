package com.giri.springsecurity1.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.giri.springsecurity1.entity.UserEntity;
import com.giri.springsecurity1.exception.ResourceNotFoundException;
import com.giri.springsecurity1.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // GET ALL USERS
    @GetMapping
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    // CREATE USER
    @PostMapping
    public UserEntity createUser(@RequestBody UserEntity user) {

        user.setPassword(
                passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    // GET USER BY ID
    @GetMapping("/{id}")
    public UserEntity getUserById(@PathVariable Long id) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
    }

    // UPDATE USER
    @PutMapping("/{id}")
    public UserEntity updateUser(
            @PathVariable Long id,
            @RequestBody UserEntity user) {

        UserEntity userData = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        userData.setName(user.getName());
        userData.setEmail(user.getEmail());

        return userRepository.save(userData);
    }

    // DELETE USER
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

        UserEntity userData = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        userRepository.delete(userData);

        return ResponseEntity.ok().build();
    }
}