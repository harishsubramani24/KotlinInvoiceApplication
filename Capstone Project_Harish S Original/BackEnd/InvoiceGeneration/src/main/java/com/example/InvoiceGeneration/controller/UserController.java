package com.example.InvoiceGeneration.controller;

import com.example.InvoiceGeneration.entity.User;
import com.example.InvoiceGeneration.service.AuthenticationService;
import com.example.InvoiceGeneration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        logger.info("Attempting to register user with username: {}", user.getUserName());
        try {
            User newUser = userService.registerUser(user);
            logger.info("User registered successfully with username: {}", newUser.getUserName());
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error registering user: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User user) {
        logger.info("Attempting to log in user with username: {}", user.getUserName());
        try {
            Optional<User> authenticatedUser = authenticationService.authenticateUser(user.getUserName(), user.getPassword());
            if (authenticatedUser.isPresent()) {
                logger.info("User logged in successfully with username: {}", authenticatedUser.get().getUserName());
                return new ResponseEntity<>(authenticatedUser.get(), HttpStatus.OK);
            } else {
                logger.warn("Login attempt failed for user with username: {}", user.getUserName());
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            logger.error("Error logging in user: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        logger.info("Fetching user with ID: {}", id);
        try {
            Optional<User> user = userService.getUserById(id);
            if (user.isPresent()) {
                logger.info("User found with username: {}", user.get().getUserName());
                return new ResponseEntity<>(user.get(), HttpStatus.OK);
            } else {
                logger.warn("User with ID {} not found", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error fetching user with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        logger.info("Fetching all users");
        try {
            List<User> users = userService.getAllUsers();
            logger.info("Number of users found: {}", users.size());
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching all users: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        logger.info("Updating user with ID: {}", id);
        try {
            Optional<User> existingUser = userService.getUserById(id);
            if (existingUser.isPresent()) {
                user.setId(id);
                User updatedUser = userService.updateUser(user);
                logger.info("User updated successfully with username: {}", updatedUser.getUserName());
                return new ResponseEntity<>(updatedUser, HttpStatus.OK);
            } else {
                logger.warn("User with ID {} not found", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error updating user with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        logger.info("Attempting to delete user with ID: {}", id);
        try {
            Optional<User> user = userService.getUserById(id);
            if (user.isPresent()) {
                userService.deleteUser(id);
                logger.info("User with ID {} and username {} deleted successfully", id, user.get().getUserName());
                return ResponseEntity.ok("User with ID " + id + " has been successfully deleted");
            } else {
                logger.warn("User with ID {} not found", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + id + " not found");
            }
        } catch (Exception e) {
            logger.error("Error deleting user with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>("Failed to delete user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}