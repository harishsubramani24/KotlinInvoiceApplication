package com.example.InvoiceGeneration.entity;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;

@Entity
@Builder
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne 
    @JoinColumn(name = "user_id", nullable = false) 
    private User user;

    @NotBlank(message = "Client name is required")
    @Column(name = "client_name", nullable = false, length = 50)
    private String clientName;

    @NotNull(message = "Amount is required")
    @Min(value = 3000, message = "Amount must be at least INR 3000")
    private Double amount;

    @PastOrPresent(message = "Date must be past or present")
    private LocalDateTime date;

    @NotBlank(message = "Description is required")
    private String description;

    public Invoice() {}

    public Invoice(User user, String clientName, Double amount, LocalDateTime date, String description) {
        this.user = user;
        this.clientName = clientName;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    

}