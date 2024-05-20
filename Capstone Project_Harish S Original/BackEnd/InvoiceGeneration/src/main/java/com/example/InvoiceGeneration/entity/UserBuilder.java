package com.example.InvoiceGeneration.entity;

import java.time.LocalDateTime;

public class UserBuilder {
    private Long id;
    private String username;
    private String password;
    private String email;
    private LocalDateTime creationDate;

    private UserBuilder() {
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public UserBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public UserBuilder username(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder creationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public User build() {
        User user = new User();
        user.setId(this.id);
        user.setUserName(this.username);
        user.setPassword(this.password);
        user.setEmail(this.email);
        user.setCreationDate(this.creationDate);
        return user;
    }
}