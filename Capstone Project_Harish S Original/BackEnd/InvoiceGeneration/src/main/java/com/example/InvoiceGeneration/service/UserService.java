package com.example.InvoiceGeneration.service;

import com.example.InvoiceGeneration.entity.User;
import com.example.InvoiceGeneration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        //  if the email or user name already exists
        Optional<User> existingUserByEmail = userRepository.findByEmail(user.getEmail());
        Optional<User> existingUserByUsername = userRepository.findByUserName(user.getUserName());
        
        if (existingUserByEmail.isPresent() || existingUserByUsername.isPresent()) {
            // If the email or username already exists, return null 
            return null;
        }

        // If the email doesn't exist, proceed with user registration
        user.setCreationDate(LocalDateTime.now());
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}