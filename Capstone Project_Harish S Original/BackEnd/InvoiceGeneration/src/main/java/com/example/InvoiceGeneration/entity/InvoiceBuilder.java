package com.example.InvoiceGeneration.entity;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public class InvoiceBuilder {

    private Long userId;
    private String clientName;
    private double amount;
    private LocalDateTime date;
    private String description;

    public Invoice build() {
        Invoice invoice = new Invoice();
        invoice.setId(userId);
        invoice.setClientName(clientName);
        invoice.setAmount(amount);
        invoice.setDate(date);
        invoice.setDescription(description);
        return invoice;
    }

    public Long getUserId() {
        return userId;
    }

    public InvoiceBuilder userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getClientName() {
        return clientName;
    }

    public InvoiceBuilder clientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public InvoiceBuilder amount(double amount) {
        this.amount = amount;
        return this;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public InvoiceBuilder date(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public InvoiceBuilder description(String description) {
        this.description = description;
        return this;
    }
}