package com.example.InvoiceGeneration.repository;

import com.example.InvoiceGeneration.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice>findByUserId(Long userId); 
    
}