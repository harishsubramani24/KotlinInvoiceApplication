package com.example.InvoiceGeneration.dto;
import java.time.LocalDateTime;
import java.util.List;

public class UserDTO {
    private Long id;
    private String userName;
    private String email;
    private LocalDateTime creationDate;
    private List<InvoiceDTO> invoices;

    public UserDTO() {}
    public UserDTO(Long id, String userName, String email, LocalDateTime creationDate, List<InvoiceDTO> invoices) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.creationDate = creationDate;
        this.invoices = invoices;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public List<InvoiceDTO> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<InvoiceDTO> invoices) {
        this.invoices = invoices;
    }

   
    }
