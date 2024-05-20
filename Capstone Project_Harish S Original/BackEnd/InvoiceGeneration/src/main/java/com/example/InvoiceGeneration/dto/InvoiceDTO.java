package com.example.InvoiceGeneration.dto;

import java.time.LocalDate;

public class InvoiceDTO {

    private String clientName;
    private Double amount;
    private LocalDate invoiceDate;
    private String description;
    private Long userId;
    public InvoiceDTO() {}

    
    public InvoiceDTO(String clientName, Double amount, LocalDate invoiceDate, String description, Long userId) {
        this.clientName = clientName;
        this.amount = amount;
        this.invoiceDate = invoiceDate;
        this.description = description;
        this.userId = userId;
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

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
